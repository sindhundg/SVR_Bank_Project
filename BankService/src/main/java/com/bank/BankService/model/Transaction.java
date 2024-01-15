package com.bank.BankService.model;





public class Transaction {


    private String senderName;
    private long senderAccountNumber;
    private String senderBankIfsc;
    private String receiverName;
    private long receiverAccountNumber;
    private String receiverBankIfsc;
    private double transactionAmount;


    public Transaction() {
    }

    public Transaction(String senderName, long senderAccountNumber, String senderBankIfsc, String receiverName, long receiverAccountNumber, String receiverBankIfsc, double transactionAmount) {
        this.senderName = senderName;
        this.senderAccountNumber = senderAccountNumber;
        this.senderBankIfsc = senderBankIfsc;
        this.receiverName = receiverName;
        this.receiverAccountNumber = receiverAccountNumber;
        this.receiverBankIfsc = receiverBankIfsc;
        this.transactionAmount = transactionAmount;
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

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
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
                "senderName='" + senderName + '\'' +
                ", senderAccountNumber=" + senderAccountNumber +
                ", senderBankIfsc='" + senderBankIfsc + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", receiverAccountNumber=" + receiverAccountNumber +
                ", receiverBankIfsc='" + receiverBankIfsc + '\'' +
                ", transactionAmount=" + transactionAmount +
                '}';
    }
}
