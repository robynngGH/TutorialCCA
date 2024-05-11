package com.ccsw.tutorial.common.custom_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author rnavarro
 */
@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class MoreThanTwoGamesLoanedForCustomerShareDateException extends RuntimeException {
    public MoreThanTwoGamesLoanedForCustomerShareDateException(String message) {
        super(message);
    }
}
