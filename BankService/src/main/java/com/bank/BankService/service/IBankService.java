package com.bank.BankService.service;

import com.bank.BankService.model.Account;

public interface IBankService {
    public Account createAccount(Account account);
    public boolean deleteAccount(long accountNumber);
    public long showBalance(long accountNumber, int pin);
}
