package com.bank.BankService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = "Account already exists")
public class AccountAlreadyExists extends Exception{
    public AccountAlreadyExists(String message){
        super(message);
    }
}
