package com.bank.BankService.model;
// Bank class for bank details
public class Bank {
    private String IFSC;
    private String bankName;
    private String branchName;



    public Bank() {
    }

    public Bank(String IFSC, String bankName, String branchName) {
        this.IFSC = IFSC;
        this.bankName = bankName;
        this.branchName = branchName;
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

    @Override
    public String toString() {
        return "Bank{" +
                "IFSC='" + IFSC + '\'' +
                ", bankName='" + bankName + '\'' +
                ", branchName='" + branchName + '\'' +
                '}';
    }
}
