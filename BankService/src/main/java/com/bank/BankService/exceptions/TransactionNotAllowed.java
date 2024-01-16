package com.bank.BankService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE,reason = "Sender and receiver account number cannot be sane")
public class TransactionNotAllowed extends Exception{
    public TransactionNotAllowed(String msg){
        super(msg);
    }
}
