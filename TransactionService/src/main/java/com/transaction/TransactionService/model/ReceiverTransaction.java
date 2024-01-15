package com.transaction.TransactionService.model;

import java.util.Date;

public class ReceiverTransaction {
    private long transactionId;
    private String senderName;
    private long senderAccountNumber;
    private String senderBankIfsc;

    private double transactionAmount;
    private Date transactionDate;

    public ReceiverTransaction() {
    }

    public ReceiverTransaction(long transactionId, String senderName, long senderAccountNumber, String senderBankIfsc, double transactionAmount, Date transactionDate) {
        this.transactionId = transactionId;
        this.senderName = senderName;
        this.senderAccountNumber = senderAccountNumber;
        this.senderBankIfsc = senderBankIfsc;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
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
}
