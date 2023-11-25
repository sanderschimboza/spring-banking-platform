package zw.co.equals.transactionservice.services;

import zw.co.equals.transactionservice.dto.FundsTransferDto;
import zw.co.equals.transactionservice.models.Transaction;

import java.util.List;

public interface TransactionService {
    Double depositFunds(String accountNumber, int amount);
    String withdrawFunds(String accountNumber, int amount);
    String transferFunds(FundsTransferDto fundsTransferDto);
    List<Transaction> generateAccountStatement(String account, String startDate, String endDate);
    Transaction findTransaction(String transRef);
    Double getAccountBalance(String accountNumber);
    List<Transaction> findUserTransactions(String account);

}
