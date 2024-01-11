package com.bank.BankService.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
@Document
public class Bank {
    @Id
//    @GeneratedValue(strategy=GenerationType.AUTO)
    private double bankid;
    private String IFSC;
    private String bankName;
    private String branchName;
    private Account account;
    private String accountHolderName;

    public Bank() {
    }

    public Bank( String IFSC, String bankName, String branchName, Account account,String accountHolderName) {

        this.IFSC = IFSC;
        this.bankName = bankName;
        this.branchName = branchName;
        this.account = account;
        this.accountHolderName=accountHolderName;
    }

    public double getBankid() {
        return bankid;
    }

    public void setBankid(double bankid) {
        this.bankid = bankid;
    }

    public String getIFSC() {
        return IFSC;
    }

    public void setIFSC(String IFSC) {
        this.IFSC = IFSC;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "bankid=" + bankid +
                ", IFSC='" + IFSC + '\'' +
                ", bankName='" + bankName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", account=" + account +
                ", accountHolderName='" + accountHolderName + '\'' +
                '}';
    }
}
