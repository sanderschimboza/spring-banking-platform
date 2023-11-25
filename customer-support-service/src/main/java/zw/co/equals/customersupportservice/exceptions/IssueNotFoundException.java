package zw.co.equals.customersupportservice.exceptions;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class IssueNotFoundException extends RuntimeException{
    public IssueNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}