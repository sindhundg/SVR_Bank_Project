package com.bank.BankService.service;

import com.bank.BankService.exceptions.AccountAlreadyExists;
import com.bank.BankService.exceptions.AccountNotFound;
import com.bank.BankService.exceptions.InvalidPin;
import com.bank.BankService.model.Account;


import java.util.List;

public interface IBankService {
    public Account createAccount(Account account) throws AccountAlreadyExists;
    public boolean deleteAccount(long accountNumber, int pin) throws AccountNotFound, InvalidPin;
    public double showBalance(long accountNumber, int pin) throws InvalidPin, AccountNotFound;
    public Account fetchCustomerAccount(long accountNumber, int pin) throws AccountNotFound, InvalidPin;
    public List<Account> fetchAllCustomerAccounts(String accountHolderName) throws AccountNotFound;
    public boolean updateAccountDetails(long accountNumber, int pin, Account account) throws AccountNotFound, InvalidPin;

    public boolean updateAccountEmail(long accountNumber, int pin, String email) throws AccountNotFound, InvalidPin;
    public boolean updateAccountPhoneNo(long accountNumber, int pin, long PhoneNo) throws  AccountNotFound, InvalidPin;

    public boolean updateAccountPin(long accountNumber, int pin, int newPin) throws  AccountNotFound, InvalidPin;
}
