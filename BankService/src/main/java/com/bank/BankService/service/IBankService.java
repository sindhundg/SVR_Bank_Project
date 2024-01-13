package com.bank.BankService.service;

import com.bank.BankService.exceptions.AccountAlreadyExists;
import com.bank.BankService.exceptions.AccountNotFound;
import com.bank.BankService.exceptions.InsufficientBalance;
import com.bank.BankService.exceptions.InvalidPin;
import com.bank.BankService.model.Account;


import java.util.List;

public interface IBankService {
    public Account createAccount(Account account) throws AccountAlreadyExists;
    public boolean deleteAccount(long accountNumber, int pin) throws AccountNotFound;
    public double showBalance(long accountNumber, int pin) throws InvalidPin, AccountNotFound;
    public Account fetchCustomerAccount(long accountNumber, int pin) throws AccountNotFound;
    public Account fetchAccount(long accountNumber) throws AccountNotFound;
    public List<Account> fetchAllCustomerAccounts(String accountHolderName) throws AccountNotFound;
    public boolean updateAccountDetails(long accountNumber, int pin, Account account) throws  AccountNotFound;

    public boolean updateAccountEmail(long accountNumber, int pin, String email) throws AccountNotFound;
    public boolean updateAccountPhoneNo(long accountNumber, int pin, long PhoneNo) throws  AccountNotFound;

    public boolean updateAccountPin(long accountNumber, int pin, int newPin) throws  AccountNotFound;
    public boolean sendAmount(long accountNumber, int pin, double amount,Account reciverAccount) throws AccountNotFound, InsufficientBalance;
}
