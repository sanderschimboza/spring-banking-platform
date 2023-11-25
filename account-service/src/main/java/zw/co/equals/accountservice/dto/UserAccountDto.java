package zw.co.equals.accountservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import zw.co.equals.accountservice.enums.AccountType;

@Data
@Builder
public class UserAccountDto {
    @NotEmpty(message = "Account number should not be empty")
    private String accountNumber;
    @NotEmpty(message = "Account number should not be empty")
    private String accountName;
    private Integer accountBal;
    private String accountDetails;
    @NotNull(message = "Account type cannot be empty")
    private AccountType accountType;
}
