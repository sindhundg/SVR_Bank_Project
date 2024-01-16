package com.bank.BankService.controller;

import com.bank.BankService.exceptions.AccountAlreadyExists;
import com.bank.BankService.exceptions.AccountNotFound;
import com.bank.BankService.exceptions.InvalidAccountNumberOrPin;
import com.bank.BankService.model.Account;
import com.bank.BankService.model.Bank;
import com.bank.BankService.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("bank")
public class BankController {
    @Autowired
    private BankService bankService;
    public static long getRandomNumber() {

        Random rnd = new Random();
        return rnd.nextLong(999999999)*2561;


    }
    @PostMapping("createAccount/{bankName}/{branchName}")
    public ResponseEntity<?> createAccount(@PathVariable String bankName, @PathVariable String branchName,@RequestBody Account account) throws AccountAlreadyExists {
       try {
           String IFSC = "";
           if(bankName.equals("SBI")){
               IFSC="SBI1234"+branchName.toUpperCase();
           } else if (bankName.equals("HDFC")) {
               IFSC="HDFC1234"+branchName.toUpperCase();
           } else if (bankName.equals("AXIS")) {
               IFSC="AXIS1234"+branchName.toUpperCase();
           }
           else if (bankName.equals("ICICI")) {
               IFSC="ICICI1234"+branchName.toUpperCase();
           }
           long accountNumber =  getRandomNumber();
           long millis = System.currentTimeMillis() % 1000;
           Bank b = new Bank(IFSC,bankName,branchName);
           account.setId(accountNumber+millis);
           account.setBank(b);
           account.setAccountHolderName(account.getAccountHolderName().toUpperCase());
           account.setAccountNumber(accountNumber);
           account.setEmail(account.getEmail());
           account.setPhoneNo(account.getPhoneNo());
           account.setPin(account.getPin());
           account.setBalance(account.getBalance());
           bankService.createAccount(account);
           return new ResponseEntity<>(b, HttpStatus.CREATED);
       }
       catch (AccountAlreadyExists ae){
           throw new AccountAlreadyExists("Account already exists");
       }
       catch (Exception e){
           return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
       }

    }
    @GetMapping("showBalance/{accountNumber}/{pin}")
    public ResponseEntity<?> getBalance(@PathVariable long accountNumber, @PathVariable int pin) throws  InvalidAccountNumberOrPin {
       try{
           double balance = bankService.showBalance(accountNumber,pin);
            return new ResponseEntity<>(balance,HttpStatus.OK);}
       catch (InvalidAccountNumberOrPin iap){
           throw  new InvalidAccountNumberOrPin("Either account number or pin is invalid");
       }
       catch (Exception e){
           return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
    @GetMapping("getAccount/{accountNumber}/{pin}")
    public ResponseEntity<?> fetchAccount(@PathVariable long accountNumber, @PathVariable int pin) throws InvalidAccountNumberOrPin {
       try{ Account account = bankService.fetchCustomerAccount(accountNumber,pin);
        return new ResponseEntity<>(account,HttpStatus.OK);}

       catch (InvalidAccountNumberOrPin ane){
           throw  new InvalidAccountNumberOrPin("Either account number or pin is invalid");
       }
       catch (Exception e){
           return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
    @GetMapping("getAccounts/{accountHolderName}")
    public ResponseEntity<?> getAccounts(@PathVariable String accountHolderName) throws AccountNotFound {
      try {
          List<Account> customerAccounts = bankService.fetchAllCustomerAccounts(accountHolderName.toUpperCase());
          return new ResponseEntity<>(customerAccounts, HttpStatus.OK);
      }
      catch (AccountNotFound ane){
          throw  new AccountNotFound("Account does not exist");
      }
      catch (Exception e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
@DeleteMapping("deleteAccount/{accountNumber}/{pin}")
    public ResponseEntity<?> deleteAccountByAccountNumber(@PathVariable long accountNumber, @PathVariable int pin) throws AccountNotFound {
      try{  bankService.deleteAccount(accountNumber,pin);
        return new ResponseEntity<>("Account deleted Successfully",HttpStatus.OK);}
      catch (InvalidAccountNumberOrPin ane){
          throw  new AccountNotFound("Either account number or pin is invalid");
      }
      catch (Exception e){
          return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
      }
}

    @PutMapping("updateAccount/{accountNumber}/{pin}")
    public ResponseEntity<?> updateAccountDetails(@PathVariable long accountNumber, @PathVariable int pin, @RequestBody Account acc) throws InvalidAccountNumberOrPin {
    try {
        bankService.updateAccountDetails(accountNumber, pin, acc);
        return new ResponseEntity<>("Account Details Updated Successfully", HttpStatus.OK);
    }
    catch(InvalidAccountNumberOrPin ae)
    {
        throw new InvalidAccountNumberOrPin("Either account number or pin is invalid");
    }
    catch(Exception ex)
    {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    @PutMapping("updateAccountEmail/{accountNumber}/{pin}/{email}")
    public ResponseEntity<?> updateAccountEmail(@PathVariable long accountNumber, @PathVariable int pin, @PathVariable String email) throws  InvalidAccountNumberOrPin {
        try {

            bankService.updateAccountEmail(accountNumber, pin, email);
            return new ResponseEntity<>("Account Details Updated Successfully", HttpStatus.OK);
        }
        catch(InvalidAccountNumberOrPin ae)
        {
            throw new InvalidAccountNumberOrPin("Either account number or pin is invalid");
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updateAccountPhoneNo/{accountNumber}/{pin}/{phoneNo}")
    public ResponseEntity<?> updateAccountPhoneNo(@PathVariable long accountNumber, @PathVariable int pin, @PathVariable long phoneNo) throws InvalidAccountNumberOrPin {
        try {

            bankService.updateAccountPhoneNo(accountNumber, pin, phoneNo);
            return new ResponseEntity<>("Account Details Updated Successfully", HttpStatus.OK);
        }
        catch(InvalidAccountNumberOrPin ae)
        {
            throw new InvalidAccountNumberOrPin("Either account number or pin is invalid");
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("updateAccountPin/{accountNumber}/{pin}/{newPin}")
    public ResponseEntity<?> updateAccountPin(@PathVariable long accountNumber, @PathVariable int pin, @PathVariable int newPin) throws InvalidAccountNumberOrPin {
        try {

            bankService.updateAccountPin(accountNumber, pin, newPin);
            return new ResponseEntity<>("Account Details Updated Successfully", HttpStatus.OK);
        }
        catch(InvalidAccountNumberOrPin ae)
        {
            throw new InvalidAccountNumberOrPin("Either account number or pin is invalid");
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("transaction/{accountNumber}/{pin}/{amount}")
    public ResponseEntity<?> makeTransaction(@PathVariable long accountNumber,@PathVariable int pin, @PathVariable double amount, @RequestBody Account receiver){
        try
        {
            bankService.sendAmount(accountNumber,pin,amount,receiver);
            Account senderAccount =bankService.fetchCustomerAccount(accountNumber,pin);
            Account recieverAccount =bankService.fetchAccount(receiver.getAccountNumber());
            Bank sb =senderAccount.getBank();
            Bank rb =recieverAccount.getBank();
            bankService.sendTransactionData(senderAccount.getAccountHolderName(),accountNumber,sb.getIFSC(),recieverAccount.getAccountHolderName(),receiver.getAccountNumber(),rb.getIFSC(),amount);
            return new ResponseEntity<>("Transaction successful",HttpStatus.OK);
        }

        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
