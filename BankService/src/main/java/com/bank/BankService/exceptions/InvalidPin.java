package com.bank.BankService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE,reason = "Invalid pin")
public class InvalidPin extends Exception{
    public InvalidPin(String message){
        super(message);
    }
}
