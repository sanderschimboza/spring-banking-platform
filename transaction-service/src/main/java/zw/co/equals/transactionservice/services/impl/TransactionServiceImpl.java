package zw.co.equals.transactionservice.services.impl;

import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import zw.co.equals.transactionservice.dto.FundsTransferDto;
import zw.co.equals.transactionservice.dto.UserAccountDto;
import zw.co.equals.transactionservice.enums.TransactionType;
import zw.co.equals.transactionservice.exceptions.TransactionServiceCustomException;
import zw.co.equals.transactionservice.models.Transaction;
import zw.co.equals.transactionservice.remote.RemoteAccountService;
import zw.co.equals.transactionservice.repositories.TransactionRepository;
import zw.co.equals.transactionservice.services.ValidationService;
import zw.co.equals.transactionservice.services.TransactionService;
import zw.co.equals.transactionservice.utils.CodeGenerator;
import zw.co.equals.transactionservice.utils.Constants;
import zw.co.equals.transactionservice.utils.DateFormatter;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ValidationService validationService;
    private final RemoteAccountService remoteAccountService;
    private final RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    public Double depositFunds(String accountNumber, int depAmount) {

        log.info("Going to deposit {} into account {}", depAmount, accountNumber);

        //perform account & amount validations first
        UserAccountDto userAccountDto = validateUserAccount(accountNumber);
        validateAmount(TransactionType.DIRECT_DEPOSIT, depAmount);
        userAccountDto.setAccountBal(userAccountDto.getAccountBal() + depAmount);

        //build transaction dto for record keeping
        Transaction transaction = Transaction.builder()
                .amount(depAmount)
                .transactionDate(DateFormatter.format(new Date()))
                .transactionType(TransactionType.DIRECT_DEPOSIT)
                .toAccount(accountNumber)
                .transactionRef(CodeGenerator.generateId("DF", 3))
                .description("Direct Deposit performed")
                .build();

        //update account balances once done and save transaction details
        double updatedBalance = updateUserAccountBalance(userAccountDto);
        transactionRepository.save(transaction);

        log.info("Deposit  successful. New Bal => {}", updatedBalance);

        //publish alert to rabbitMQ Queue
        publishAlertToRabbitQueue(transaction);
        return updatedBalance;
    }

    @Override
    public String withdrawFunds(String accountNumber, int withdrawalAmount) {

        log.info("Going to withdraw {} from account {}", withdrawalAmount, accountNumber);

        //perform account & amount validations first
        UserAccountDto userAccountDto = validateUserAccount(accountNumber);
        validateAmount(TransactionType.WITHDRAWAL, withdrawalAmount);

        //deduct the amount from the available balance accordingly
        int newBalance = userAccountDto.getAccountBal() - withdrawalAmount;
        //apply withdrawal charges
        int charge = applyCharges(TransactionType.WITHDRAWAL,withdrawalAmount);
        newBalance -= charge;

        if (newBalance <= 0) {
            throw new TransactionServiceCustomException(
                    "Insufficient credit to perform transaction!",
                    HttpStatus.BAD_REQUEST
            );
        }

        //update user account with the new balance
        userAccountDto.setAccountBal(newBalance);

        //build transaction dto for record keeping
        Transaction transaction = Transaction.builder()
                .amount(withdrawalAmount)
                .charge(charge)
                .transactionDate(DateFormatter.format(new Date()))
                .transactionType(TransactionType.WITHDRAWAL)
                .fromAccount(accountNumber)
                .transactionRef(CodeGenerator
                        .generateId("CF", 1))
                .description("Account Withdrawal performed")
                .build();

        updateUserAccountBalance(userAccountDto);
        transactionRepository.save(transaction);

        log.info("Withdrawal successful. Transaction ref => {}", transaction.getTransactionRef());

        publishAlertToRabbitQueue(transaction);
        return transaction.getTransactionRef();
    }

    @Override
    @Transactional
    public String transferFunds(FundsTransferDto fundsTransferDto) {
        log.info("Going to transfer ${} from account {} to account {}"
                , fundsTransferDto.getAmount() / 100, fundsTransferDto.getFromAccount(),
                fundsTransferDto.getToAccount());

        //perform account & amount validations first
        UserAccountDto fromUserAccount = validateUserAccount(fundsTransferDto.getFromAccount());
        UserAccountDto toUserAccount = validateUserAccount(fundsTransferDto.getToAccount());
        validateAmount(fundsTransferDto.getTransactionType(), fundsTransferDto.getAmount());

        //debit fromAccount and credit toAccount accordingly
        int newFromUserAccountBalance = fromUserAccount.getAccountBal() - fundsTransferDto.getAmount();
        //apply transfer charges to fromAccount
        int charge = applyCharges(fundsTransferDto.getTransactionType(),fundsTransferDto.getAmount());
        newFromUserAccountBalance -= charge;

        if (newFromUserAccountBalance <= 0) {
            throw new TransactionServiceCustomException(
                    "Insufficient credit to perform transaction!",
                    HttpStatus.BAD_REQUEST
            );
        }

        //update the accounts with new balances
        fromUserAccount.setAccountBal(newFromUserAccountBalance);
        toUserAccount.setAccountBal(toUserAccount.getAccountBal() + fundsTransferDto.getAmount());

        //build transaction dto for record keeping
        Transaction transaction = Transaction.builder()
                .amount(fundsTransferDto.getAmount())
                .charge(charge)
                .transactionDate(DateFormatter.format(new Date()))
                .transactionType(fundsTransferDto.getTransactionType())
                .fromAccount(fundsTransferDto.getFromAccount())
                .toAccount(fundsTransferDto.getToAccount())
                .transactionRef(CodeGenerator.generateId("TF", 5))
                .description("Funds Transfer")
                .build();

        updateUserAccountBalance(fromUserAccount);
        updateUserAccountBalance(toUserAccount);
        transactionRepository.save(transaction);

        log.info("Transfer successful. Transaction ref => {}", transaction.getTransactionRef());

        publishAlertToRabbitQueue(transaction);
        return transaction.getTransactionRef();
    }

    @Override
    @Cacheable(value = "UserAccount", key = "#accountNumber")
    public Double getAccountBalance(String accountNumber) {
        UserAccountDto userAccountDto = validateUserAccount(accountNumber);
        Double bal = (double) userAccountDto.getAccountBal() / 100;
        log.info("Got this balance from UserAccount service => {}", bal);
        return bal;
    }

    @Override
    public List<Transaction> generateAccountStatement(String account, String startDate, String endDate) {

        if (startDate == null && endDate == null) {
            log.info("Going to Retrieve last 5 transactions");
            return transactionRepository.findTop5ByFromAccountOrToAccountOrderByIdDesc(
                    account, account);
        }

        if (startDate == null) {
            log.info("Going to Retrieve transactions up to => {}", endDate);
            return transactionRepository.findUserTransactionsToDate(
                    account, account, DateFormatter.formatFromString(endDate));
        }

        if (endDate == null) {
            log.info("Going to Retrieve transactions from => {}", startDate);
            return transactionRepository.findUserTransactionsFromDate(
                    account, account, DateFormatter.formatFromString(startDate));
        }

        log.info("Going to Retrieve transactions from => {} up to => {}", startDate, endDate);
        return transactionRepository.findUserTransactionBetweenDates(
                account, account, DateFormatter.formatFromString(startDate),
                DateFormatter.formatFromString(endDate));
    }

    @Override
    public Transaction findTransaction(String transRef) {
        return transactionRepository.findTransactionByTransactionRef(transRef)
                .orElseThrow(() -> new TransactionServiceCustomException(
                                "Transaction was not found!", HttpStatus.NOT_FOUND
                        )
                );
    }

    private Double updateUserAccountBalance(UserAccountDto userAccountDto) {
        try {
            //update user account balance on remote account-service
            UserAccountDto userAccount = remoteAccountService.updateUserAccount(userAccountDto);
            log.info("Update account balance response => {}", userAccount);
            return (double) (userAccount.getAccountBal() / 100);

        } catch (FeignException.FeignClientException e) {
            throw new TransactionServiceCustomException(
                    e.contentUTF8(),
                    HttpStatus.valueOf(e.status())
            );
        }
    }

    @Override
    public List<Transaction> findUserTransactions(String account) {
        return transactionRepository
                .findTransactionByFromAccountOrToAccount(account, account);
    }

    private void publishAlertToRabbitQueue(Transaction transaction) {
        rabbitTemplate.convertAndSend("", Constants.TRANSACTION_ALERT_QUEUE,
                transaction);
        log.info("Published transaction alert to Queue {}", transaction);
    }

    private UserAccountDto validateUserAccount(String accountNumber) {
        return validationService.validateUserAccount(accountNumber);
    }

    private void validateAmount(TransactionType transactionType, int depAmount) {
        validationService.validateAmount(transactionType, depAmount);
    }

    private int applyCharges(TransactionType transactionType, int amount) {
        return validationService.validateCharges(transactionType, amount);
    }
}
