package com.authenticate.AuthenticationService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT, reason="Customer already found with this id")
public class CustomerAlreadyExists extends Exception{
    public CustomerAlreadyExists(String message)
    {
        super(message);
    }
}
