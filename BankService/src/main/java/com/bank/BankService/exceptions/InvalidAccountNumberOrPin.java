package com.bank.BankService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE,reason = "Either AccountNumber or Pin is invalid")
public class InvalidAccountNumberOrPin extends Exception{
    public InvalidAccountNumberOrPin(String message){
        super(message);
    }
}
