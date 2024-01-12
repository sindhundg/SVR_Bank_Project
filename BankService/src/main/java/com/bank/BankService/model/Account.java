package com.bank.BankService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Account {
    @Id
    private long id;
    private long accountNumber;
    private String accountHolderName;
    private String email;
    private long phoneNo;
    private double balance;
    private int pin;
    private Bank bank;


    public Account() {
    }

    public Account(long id, long accountNumber, String accountHolderName, String email, long phoneNo, double balance, int pin, Bank bank) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.balance = balance;
        this.pin = pin;
        this.bank = bank;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
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
                "id=" + id +
                ", accountNumber=" + accountNumber +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo=" + phoneNo +
                ", balance=" + balance +
                ", pin=" + pin +
                ", bank=" + bank +
                '}';
    }
}
