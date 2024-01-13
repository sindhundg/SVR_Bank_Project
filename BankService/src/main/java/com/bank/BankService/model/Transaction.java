package com.bank.BankService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



public class Transaction {



    private long senderAccountNumber;
    private String senderBankIfsc;
    private long receiverAccountNumber;
    private String receiverBankIfsc;

    //private String transactionType;
    private double transactionAmount;


    public Transaction() {
    }

    public Transaction(long senderAccountNumber, String senderBankIfsc, long receiverAccountNumber, String receiverBankIfsc, double transactionAmount) {
        this.senderAccountNumber = senderAccountNumber;
        this.senderBankIfsc = senderBankIfsc;
        this.receiverAccountNumber = receiverAccountNumber;
        this.receiverBankIfsc = receiverBankIfsc;
        this.transactionAmount = transactionAmount;
    }

    public long getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(long senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public String getSenderBankIfsc() {
        return senderBankIfsc;
    }

    public void setSenderBankIfsc(String senderBankIfsc) {
        this.senderBankIfsc = senderBankIfsc;
    }

    public long getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(long receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public String getReceiverBankIfsc() {
        return receiverBankIfsc;
    }

    public void setReceiverBankIfsc(String receiverBankIfsc) {
        this.receiverBankIfsc = receiverBankIfsc;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "senderAccountNumber=" + senderAccountNumber +
                ", senderBankIfsc='" + senderBankIfsc + '\'' +
                ", receiverAccountNumber=" + receiverAccountNumber +
                ", receiverBankIfsc='" + receiverBankIfsc + '\'' +
                ", transactionAmount=" + transactionAmount +
                '}';
    }
}
