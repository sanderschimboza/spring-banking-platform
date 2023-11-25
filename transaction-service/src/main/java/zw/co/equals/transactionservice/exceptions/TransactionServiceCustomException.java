package zw.co.equals.transactionservice.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransactionServiceCustomException extends RuntimeException {
    private HttpStatus httpStatus;

    public TransactionServiceCustomException(String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);
        this.httpStatus = httpStatus;
    }
}