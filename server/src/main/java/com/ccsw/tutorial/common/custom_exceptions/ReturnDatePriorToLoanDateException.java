package com.ccsw.tutorial.common.custom_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author rnavarro
 */
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class ReturnDatePriorToLoanDateException extends RuntimeException {
    public ReturnDatePriorToLoanDateException(String message) {
        super(message);
    }
}
