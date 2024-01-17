package com.transaction.TransactionService.controller;

import com.transaction.TransactionService.exceptions.TransactionNotFound;
import com.transaction.TransactionService.model.ReceiverTransaction;
import com.transaction.TransactionService.model.SenderTransaction;
import com.transaction.TransactionService.model.Transaction;
import com.transaction.TransactionService.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    // Endpoint to get all the debit details
    @GetMapping("getDebitTransactionDetails/{accountNumber}/{numberOfTransactions}")
    public ResponseEntity<?> getAllDebitTransaction(@PathVariable long accountNumber,@PathVariable int numberOfTransactions) throws TransactionNotFound {
       try {
           List<SenderTransaction> transactionList = transactionService.fetchDebitTransactions(accountNumber,numberOfTransactions);
           return new ResponseEntity<>(transactionList, HttpStatus.OK);
       }
       catch (Exception e)
       {
           return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
       }
    }
    //Endpoint to get all the credit details
    @GetMapping("getCreditTransactionDetails/{accountNumber}/{numberOfTransactions}")
    public ResponseEntity<?> getAllCreditTransaction(@PathVariable long accountNumber,@PathVariable int numberOfTransactions ) throws TransactionNotFound {
     try
     {
         List<ReceiverTransaction> transactionList = transactionService.fetchCreditTransactions(accountNumber, numberOfTransactions);
         return new ResponseEntity<>(transactionList, HttpStatus.OK);
     }
     catch (Exception e)
     {
         return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
     }
    }
    // Endpoint to view transaction history
    @GetMapping("transactionHistoryForAccount/{accountNumber}/{numberOfTransactions}")
    public ResponseEntity<?> getHistory(@PathVariable long accountNumber,@PathVariable int numberOfTransactions) throws TransactionNotFound {
        List<Transaction> t=  transactionService.getTransactionHistory(accountNumber,numberOfTransactions);
        return new ResponseEntity<>(t,HttpStatus.OK);
    }
    //Endpoint to view transaction details of all the accounts of the customer
    @GetMapping("transactionHistoryOfMultiAccounts/{accountHolderName}/{numberOfTransactions}")
    public ResponseEntity<?> getAllAcctHistory(@PathVariable String accountHolderName,@PathVariable int numberOfTransactions) throws TransactionNotFound {
        List<Transaction> t=  transactionService.getAllAcctTransactionHistory(accountHolderName.toUpperCase(),numberOfTransactions);
        return new ResponseEntity<>(t,HttpStatus.OK);
    }
}
