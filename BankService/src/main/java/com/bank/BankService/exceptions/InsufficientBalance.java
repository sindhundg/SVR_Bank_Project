package com.bank.BankService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE,reason = "Insufficient Balance")
public class InsufficientBalance extends Exception{
    public InsufficientBalance(String message){
        super(message);
    }
}
