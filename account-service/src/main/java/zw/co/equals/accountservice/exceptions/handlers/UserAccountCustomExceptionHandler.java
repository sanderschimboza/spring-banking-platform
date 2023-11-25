package zw.co.equals.accountservice.exceptions.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import zw.co.equals.accountservice.data.ErrorResponse;
import zw.co.equals.accountservice.exceptions.UserAccountCustomException;

@ControllerAdvice
class UserAccountCustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserAccountCustomException.class)
    public ResponseEntity<ErrorResponse> handleUserAccountNotFoundException(UserAccountCustomException e) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(e.getMessage())
                .build(), e.getHttpStatus());
    }
}
