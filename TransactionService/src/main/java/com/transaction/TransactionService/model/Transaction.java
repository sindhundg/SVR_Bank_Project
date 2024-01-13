package com.transaction.TransactionService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Document
public class Transaction {
    @Id
    private long transactionId = 15+System.currentTimeMillis();

    private long senderAccountNumber;
    private String senderBankIfsc;
    private long receiverAccountNumber;
    private String receiverBankIfsc;
    //private String transactionType;
    private double transactionAmount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date transactionDate = new Date();



    public Transaction() {
    }

    public Transaction(long transactionId, long senderAccountNumber, String senderBankIfsc, long receiverAccountNumber, String receiverBankIfsc, double transactionAmount, Date transactionDate) {
        this.transactionId = transactionId;
        this.senderAccountNumber = senderAccountNumber;
        this.senderBankIfsc = senderBankIfsc;
        this.receiverAccountNumber = receiverAccountNumber;
        this.receiverBankIfsc = receiverBankIfsc;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
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

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", senderAccountNumber=" + senderAccountNumber +
                ", senderBankIfsc='" + senderBankIfsc + '\'' +
                ", receiverAccountNumber=" + receiverAccountNumber +
                ", receiverBankIfsc='" + receiverBankIfsc + '\'' +
                ", transactionAmount=" + transactionAmount +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
