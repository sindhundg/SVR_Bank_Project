package com.authenticate.AuthenticationService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason="Customer not found with this id")
public class CustomerNotFound extends Exception{
    public CustomerNotFound(String message)
    {
        super(message);
    }
}
