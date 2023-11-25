package zw.co.equals.accountservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.equals.accountservice.enums.AccountType;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserAccount implements Serializable {
    @Id
    private String accountNumber;
    private String accountName;
    //account contact
    private Integer accountBal;
    private String accountDetails;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
}
