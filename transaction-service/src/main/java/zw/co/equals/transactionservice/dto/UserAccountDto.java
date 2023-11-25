package zw.co.equals.transactionservice.dto;

import lombok.Data;
import zw.co.equals.transactionservice.enums.AccountType;

@Data
public class UserAccountDto {
    private String accountNumber;
    private String accountName;
    private String accountHolder;
    private Integer accountBal;
    private String accountDetails;
    private AccountType accountType;
}
