package com.bank.BankService.service;

import com.bank.BankService.exceptions.AccountAlreadyExists;
import com.bank.BankService.exceptions.AccountNotFound;
import com.bank.BankService.model.Account;
import com.bank.BankService.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService implements IBankService{
@Autowired
    private AccountRepo bankRepo;
    @Override
    public Account createAccount(Account account) throws AccountAlreadyExists {
         Optional<Account> bopt= bankRepo.findById((double) account.getId());
        if(bopt.isPresent()){
            throw new AccountAlreadyExists("Account already exists");
        }
        else {
           Account bank1= bankRepo.save(account);
            return bank1;
        }
    }

    @Override
    public boolean deleteAccount(long accountNumber, int pin) throws AccountNotFound {
        Optional<Account> dopt = Optional.of(bankRepo.findByAccountNumberAndPin(accountNumber,pin));
        if(dopt.isEmpty())
        {
            throw  new AccountNotFound("Account does not exist");
        }
//        Account account =dopt.get();
        bankRepo.deleteByAccountNumberAndPin(accountNumber, pin);
        return true;
    }

    @Override
    public double showBalance(long accountNumber, int pin) throws AccountNotFound {
        Optional<Account> aopt = Optional.ofNullable(bankRepo.findByAccountNumberAndPin(accountNumber, pin));
        if (aopt.isEmpty()){
            throw new AccountNotFound("Account does not exist");
        }
        else {
            Account account = bankRepo.findByAccountNumberAndPin(accountNumber, pin);
            return account.getBalance();
         }
    }

    @Override
    public Account fetchCustomerAccount(long accountNumber, int pin) throws AccountNotFound {
        Optional<Account> aopt = Optional.ofNullable(bankRepo.findByAccountNumberAndPin(accountNumber, pin));
        if(aopt.isEmpty()){
            throw  new AccountNotFound("Account does not exist");
        }
        else
        {
            Account account = bankRepo.findByAccountNumberAndPin(accountNumber, pin);
            return account;
        }
    }


    @Override
    public List<Account> fetchAllCustomerAccounts(String accountHolderName) {
    List<Account> lacc = bankRepo.findByAccountHolderName(accountHolderName);
    return lacc;
    }

    @Override
    public boolean updateAccountDetails(long accountNumber, int pin, Account account) throws AccountNotFound {
        return false;
    }



}
