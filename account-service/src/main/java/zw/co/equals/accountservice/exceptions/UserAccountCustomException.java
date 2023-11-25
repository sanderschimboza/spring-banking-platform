package zw.co.equals.accountservice.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserAccountCustomException extends RuntimeException{
    private HttpStatus httpStatus;

    public UserAccountCustomException(String errorMsg, HttpStatus httpStatus) {
        super(errorMsg);
        this.httpStatus = httpStatus;
    }
}