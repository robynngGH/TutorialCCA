package com.ccsw.tutorial.common.custom_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author rnavarro
 */
@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
public class MoreThan14DaysException extends RuntimeException {
    public MoreThan14DaysException(String message) {
        super(message);
    }
}
