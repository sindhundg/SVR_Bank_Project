package com.bank.BankService.service;

import com.bank.BankService.exceptions.AccountAlreadyExists;
import com.bank.BankService.exceptions.AccountNotFound;
import com.bank.BankService.exceptions.InvalidPin;
import com.bank.BankService.model.Account;
import com.bank.BankService.model.Bank;

import java.util.List;

public interface IBankService {
    public Account createAccount(Account account) throws AccountAlreadyExists;
    public boolean deleteAccountByPin(double accountNumber) throws AccountNotFound;
    public double showBalance(double accountNumber, int pin) throws InvalidPin;
    public List<Account> fetchAllCustomerAccounts(String accountHolderName);
}
