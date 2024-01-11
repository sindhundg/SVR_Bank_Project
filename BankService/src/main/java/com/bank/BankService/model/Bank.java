package com.bank.BankService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Bank {
    @Id
    private String IFSC;
    private String bankName;
    private String branchName;
    private Account account;

    public Bank() {
    }

    public Bank(String IFSC, String bankName, String branchName, Account account) {
        this.IFSC = IFSC;
        this.bankName = bankName;
        this.branchName = branchName;
        this.account = account;
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

    @Override
    public String toString() {
        return "Bank{" +
                "IFSC='" + IFSC + '\'' +
                ", bankName='" + bankName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", account=" + account +
                '}';
    }
}
