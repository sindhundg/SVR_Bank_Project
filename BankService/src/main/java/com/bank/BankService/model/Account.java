package com.bank.BankService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
@Document
public class Account {
    @Id
    private BigInteger accountNumber;
    private BigInteger balance;
    private int pin;
    private Bank bank;

    public Account() {
    }

    public Account(BigInteger accountNumber, BigInteger balance, int pin, Bank bank) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.pin = pin;
        this.bank = bank;
    }

    public BigInteger getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(BigInteger accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", balance=" + balance +
                ", pin=" + pin +
                ", bank=" + bank +
                '}';
    }
}
