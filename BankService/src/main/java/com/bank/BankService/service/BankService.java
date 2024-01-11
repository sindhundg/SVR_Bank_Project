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
    public Bank createAccount(Bank bank) throws AccountAlreadyExists {
        Optional<Bank> bopt = bankRepo.findById(bank.getIFSC());
        if(bopt.isPresent()){
            throw new AccountAlreadyExists("Account already exists");
        }
        else {
           Bank bank1= bankRepo.save(bank);
            return bank1;
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
