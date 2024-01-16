package com.bank.BankService.service;

import com.bank.BankService.exceptions.*;

import com.bank.BankService.model.Account;


import java.util.List;

public interface IBankService {
    public Account createAccount(Account account) throws AccountAlreadyExists;
    public boolean deleteAccount(long accountNumber, int pin) throws AccountNotFound;
    public double showBalance(long accountNumber, int pin) throws InvalidPin, AccountNotFound, InvalidAccountNumberOrPin;
    public Account fetchCustomerAccount(long accountNumber, int pin) throws AccountNotFound, InvalidAccountNumberOrPin;

    Account fetchAccount(long accountNumber) throws AccountNotFound;

    public List<Account> fetchAllCustomerAccounts(String accountHolderName) throws AccountNotFound;
    public boolean updateAccountDetails(long accountNumber, int pin, Account account) throws InvalidAccountNumberOrPin;

    public boolean updateAccountEmail(long accountNumber, int pin, String email) throws InvalidAccountNumberOrPin;
    public boolean updateAccountPhoneNo(long accountNumber, int pin, long PhoneNo) throws  InvalidAccountNumberOrPin;

    public boolean updateAccountPin(long accountNumber, int pin, int newPin) throws InvalidAccountNumberOrPin, InvalidPin;

    boolean sendAmount(long accountNumber, int pin, double amount, Account reciverAccount) throws AccountNotFound, InsufficientBalance;
}
