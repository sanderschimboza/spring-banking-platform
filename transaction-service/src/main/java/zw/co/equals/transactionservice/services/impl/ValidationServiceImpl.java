package zw.co.equals.transactionservice.services.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import zw.co.equals.transactionservice.dto.UserAccountDto;
import zw.co.equals.transactionservice.enums.TransactionType;
import zw.co.equals.transactionservice.exceptions.TransactionServiceCustomException;
import zw.co.equals.transactionservice.remote.RemoteAccountService;
import zw.co.equals.transactionservice.services.ValidationService;
import zw.co.equals.transactionservice.utils.Charges;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private final RemoteAccountService remoteAccountService;

    @Override
    public void validateAmount(TransactionType transactionType, int amount) {
        switch (transactionType) {
            case WITHDRAWAL -> {
                if (amount < 500) {
                    throw new TransactionServiceCustomException(
                            "Withdrawal amount should be at-least $5",
                            HttpStatus.BAD_REQUEST
                    );
                }
            }
            case DIRECT_DEPOSIT -> {
                if (amount < 1000) {
                    throw new TransactionServiceCustomException(
                            "Direct Deposit amount should be at-least $10",
                            HttpStatus.BAD_REQUEST
                    );
                }
            }
            case INTERNAL_TRANSFER -> {
                if (amount <= 100) {
                    throw new TransactionServiceCustomException(
                            "Internal transfer amount should be grater than $1",
                            HttpStatus.BAD_REQUEST
                    );
                }
            }
            case ZIPIT -> {
                if (amount < 1000) {
                    throw new TransactionServiceCustomException(
                            "ZIPIT amount should be at-least $10",
                            HttpStatus.BAD_REQUEST
                    );
                }
            }
        }
    }

    @Override
    public Integer validateCharges(TransactionType transactionType, int amount) {
        return switch (transactionType) {
            case WITHDRAWAL -> (int) (Charges.WITHDRAWAL_CHARGE * amount);
            case ZIPIT -> (int) (Charges.ZIPIT_CHARGE * amount);
            case INTERNAL_TRANSFER -> (int) (Charges.TRANSFER_CHARGE * amount);
            default -> 0;
        };
    }

    @Override
    public UserAccountDto validateUserAccount(String accountNumber) {
        log.info("Going to validate account => {}", accountNumber);
        try {
            UserAccountDto userAccountDto = remoteAccountService.getUserAccount(accountNumber);
            log.info("Account validation response => {}", userAccountDto);
            return userAccountDto;
        } catch (FeignException.FeignClientException e) {
            throw new TransactionServiceCustomException(
                    e.contentUTF8(), HttpStatus.valueOf(e.status())
            );
        }
    }
}
