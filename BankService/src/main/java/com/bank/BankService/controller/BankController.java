package com.bank.BankService.controller;

import com.bank.BankService.exceptions.AccountAlreadyExists;
import com.bank.BankService.model.Account;
import com.bank.BankService.model.Bank;
import com.bank.BankService.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("bank")
public class BankController {
    @Autowired
    private BankService bankService;
    public static double getRandomNumber() {

        Random rnd = new Random();
        double number = rnd.nextDouble(99999999);

        return number;
    }
    @PostMapping("createAccount/{accountHolderName}/{bankName}/{branchName}")
    public ResponseEntity<?> createAccount(@PathVariable String accountHolderName,@PathVariable String bankName, @PathVariable String branchName,@RequestBody Account a) throws AccountAlreadyExists {
       try {
           String IFSC = "";
           if(bankName.equals("SBI")){
               IFSC="SBI1234";
           } else if (bankName.equals("HDFC")) {
               IFSC="HDFC1234";
           }
           double accountNumber =  getRandomNumber();
           a.setAccountNumber(accountNumber);

           Bank b = new Bank(IFSC, bankName, branchName, a, accountHolderName);
           b.setBankid(accountNumber);
           b.setIFSC(IFSC);

           bankService.createAccount(b);
           return new ResponseEntity<>(b, HttpStatus.CREATED);
       }
       catch (AccountAlreadyExists ae){
           throw new AccountAlreadyExists("Account already exists");
       }
       catch (Exception e){
           return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
       }

    }
    @GetMapping("getAccounts/{accountHolderName}")
    public ResponseEntity<?> getAccounts(@PathVariable String accountHolderName){
        List<Bank> customerAccounts= bankService.fetchAllCustomerAccounts(accountHolderName);
        return new ResponseEntity<>(customerAccounts,HttpStatus.OK);

    }
}
