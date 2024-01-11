package com.bank.BankService.service;

import com.bank.BankService.model.Account;
import com.bank.BankService.repository.BankRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankService implements IBankService{
@Autowired
    private BankRepo bankRepo;
    @Override
    public Account createAccount(Account account) {
        return null;
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
