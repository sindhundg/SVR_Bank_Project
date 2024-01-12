package com.bank.BankService.service;

import com.bank.BankService.exceptions.AccountAlreadyExists;
import com.bank.BankService.model.Account;
import com.bank.BankService.model.Bank;
import com.bank.BankService.repository.BankRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankService implements IBankService{
@Autowired
    private BankRepo bankRepo;
    @Override
    public Account createAccount(Account account) throws AccountAlreadyExists {
        Optional<Account> accObj = bankRepo.findById(account.getAccountNumber());
        if(accObj.isPresent()){
            throw new AccountAlreadyExists("Account already exists");
        }
        else {
           Account acc = bankRepo.save(account);
            return acc;
        }
    }

    @Override
    public boolean deleteAccount(long accountNumber) {
        return false;
    }

    @Override
    public long showBalance(long accountNumber, int pin) {
        return 0;
    }
}
