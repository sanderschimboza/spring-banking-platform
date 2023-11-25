package zw.co.equals.transactionservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import zw.co.equals.transactionservice.enums.TransactionType;

import java.io.Serializable;
import java.util.Date;

@Table(name = "transactions")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fromAccount;
    private String toAccount;
    private Integer amount;
    private Integer charge;
    private Date transactionDate;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private String transactionRef;
    private String description;
}
