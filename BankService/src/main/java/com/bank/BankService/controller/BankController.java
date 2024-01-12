package com.bank.BankService.controller;

import com.bank.BankService.exceptions.AccountAlreadyExists;
import com.bank.BankService.model.Account;
import com.bank.BankService.model.Bank;
import com.bank.BankService.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Random;

@RestController
@RequestMapping("bank")
public class BankController {
    @Autowired
    private BankService bankService;
    public static BigInteger getRandomNumberString() {

        Random rnd = new Random();
        BigInteger number = new BigInteger(String.valueOf(rnd.nextInt(999999)));

        return number;
    }
    @PostMapping("createAccount/{bankName}/{branchName}")
    public ResponseEntity<?> createAccount(@PathVariable String bankName, @PathVariable String branchName,@RequestBody Account a) throws AccountAlreadyExists {
       try {
           String IFSC = "";
           if(bankName.equals("SBI")){
               IFSC="SBI1234";
           } else if (bankName.equals("HDFC")) {
               IFSC="HDFC1234";
           }
           BigInteger accountNumber =  getRandomNumberString();
           a.setAccountNumber(accountNumber);
           Bank b = new Bank(IFSC, bankName, branchName);
           a.setBank(b);
           Account acc = bankService.createAccount(a);
           return new ResponseEntity<>(acc, HttpStatus.CREATED);
       }
       catch (AccountAlreadyExists ae){
           throw new AccountAlreadyExists("Account already exists");
       }
       catch (Exception e){
           return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
       }

    }
}
