package com.bank.BankService.service;

import com.bank.BankService.exceptions.AccountAlreadyExists;
import com.bank.BankService.model.Account;
import com.bank.BankService.model.Bank;
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
        Optional<Account> bopt = bankRepo.findById(account.getAccountNumber());
        if(bopt.isPresent()){
            throw new AccountAlreadyExists("Account already exists");
        }
        else {
           Account bank1= bankRepo.save(account);
            return bank1;
        }
    }

    @Override
    public boolean deleteAccountByPin(double accountNumber) {

        return true;
    }

    @Override
    public double showBalance(double accountNumber, int pin) {
        return 0;
    }

    @Override
    public List<Account> fetchAllCustomerAccounts(String accountHolderName) {
    List<Account> lacc = bankRepo.findByAccountHolderName(accountHolderName);
    return lacc;
    }
}
