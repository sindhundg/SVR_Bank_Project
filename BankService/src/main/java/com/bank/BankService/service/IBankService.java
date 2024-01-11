package com.bank.BankService.service;

import com.bank.BankService.exceptions.AccountAlreadyExists;
import com.bank.BankService.exceptions.AccountNotFound;
import com.bank.BankService.exceptions.InvalidPin;
import com.bank.BankService.model.Account;
import com.bank.BankService.model.Bank;

public interface IBankService {
    public Bank createAccount(Bank bank) throws AccountAlreadyExists;
    public boolean deleteAccount(long accountNumber) throws AccountNotFound;
    public long showBalance(long accountNumber, int pin) throws InvalidPin;
}
