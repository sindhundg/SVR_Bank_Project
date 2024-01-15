package com.transaction.TransactionService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "No Transactions found for this account number")
public class TransactionNotFound extends Exception{
    public TransactionNotFound(String message){
        super(message);
    }
}
