package zw.co.equals.transactionservice.exceptions.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import zw.co.equals.transactionservice.exceptions.TransactionServiceCustomException;

@ControllerAdvice
class TransactionServiceExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(TransactionServiceCustomException.class)
    public ResponseEntity<String> handleTransactionServiceException(TransactionServiceCustomException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }
}