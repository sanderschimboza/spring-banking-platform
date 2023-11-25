package zw.co.equals.customersupportservice.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import zw.co.equals.customersupportservice.data.ErrorResponse;
import zw.co.equals.customersupportservice.exceptions.IssueNotFoundException;

@ControllerAdvice
class UserAccountNotFoundExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(IssueNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserAccountNotFoundException(IssueNotFoundException e) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(e.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }
}
