package com.ccsw.tutorial.common.custom_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author rnavarro
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class SameGameLoanedShareDateException extends RuntimeException {
    public SameGameLoanedShareDateException(String message) {
        super(message);
    }
}
