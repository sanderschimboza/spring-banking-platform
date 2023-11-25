package zw.co.equals.transactionservice.services;

import zw.co.equals.transactionservice.dto.UserAccountDto;
import zw.co.equals.transactionservice.enums.TransactionType;

public interface ValidationService {
    void validateAmount(TransactionType transactionType, int amount);

    Integer validateCharges(TransactionType transactionType, int amount);

    UserAccountDto validateUserAccount(String accountNumber);

}
