package com.transaction.TransactionService.controller;

import com.transaction.TransactionService.exceptions.TransactionNotFound;
import com.transaction.TransactionService.model.Transaction;
import com.transaction.TransactionService.service.TransactService;
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
    TransactService transactService;
    @GetMapping("getDebitTransactionDetails/{accountNumber}")
    public ResponseEntity<?> getAllDebitTransaction(@PathVariable long accountNumber) throws TransactionNotFound {
       try {
           List<Transaction> transactionList = transactService.fetchDebitTransactions(accountNumber);
           return new ResponseEntity<>(transactionList, HttpStatus.OK);
       }
//       catch (TransactionNotFound te){
//           throw  new TransactionNotFound("Transaction does not exist");
//       }
       catch (Exception e)
       {
           return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
       }
    }
    @GetMapping("getCreditTransactionDetails/{accountNumber}")
    public ResponseEntity<?> getAllCreditTransaction(@PathVariable long accountNumber) throws TransactionNotFound {
     try
     {
         List<Transaction> transactionList = transactService.fetchCreditTransactions(accountNumber);
         return new ResponseEntity<>(transactionList, HttpStatus.OK);
     }
     catch (Exception e)
     {
         return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
     }
    }

}
