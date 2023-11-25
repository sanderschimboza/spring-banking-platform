package zw.co.equals.transactionservice.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.equals.transactionservice.enums.TransactionType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundsTransferDto {
    @NotBlank(message = "Source Account cannot be empty")
    private String fromAccount;
    @NotBlank(message = "Destination Account cannot be empty")
    private String toAccount;
    @NotNull(message = "Transfer Amount cannot be empty")
    private Integer amount;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Transaction type cannot be empty")
    private TransactionType transactionType;
}
