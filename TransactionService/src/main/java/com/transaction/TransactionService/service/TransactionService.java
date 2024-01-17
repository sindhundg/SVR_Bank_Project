package com.transaction.TransactionService.service;

import com.transaction.TransactionService.exceptions.TransactionNotFound;
import com.transaction.TransactionService.model.ReceiverTransaction;
import com.transaction.TransactionService.model.SenderTransaction;
import com.transaction.TransactionService.model.Transaction;
import com.transaction.TransactionService.repository.TransactionRepo;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import com.transaction.TransactionService.rabbitmqconfiguration.DataFormat;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepo transactionRepo;

    // Recieves the data from bank service
    @RabbitListener(queues = "TransactionQueue")
    public void receiveDataFromProducer(DataFormat df) throws JsonProcessingException {
        ObjectMapper objMap = new ObjectMapper();
        Object msg1 = df.getJsonObject().get("Transaction");
        String jsonString = objMap.writeValueAsString(msg1);
        Transaction transaction = objMap.readValue(jsonString, Transaction.class);
        transactionRepo.save(transaction);
        System.out.println(df.getJsonObject().toJSONString());
    }

    // Method to view debit details
public List<SenderTransaction> fetchDebitTransactions(long accountNumber,int numberOfTransactions) throws TransactionNotFound {
      List<Transaction>  debitList = transactionRepo.findBySenderAccountNumber(accountNumber);
      Collections.sort(debitList,(t1,t2)->t1.getTransactionDate().compareTo(t2.getTransactionDate()));
      Collections.reverse(debitList);
        List<SenderTransaction> filteredDebitList = new ArrayList<>();
        for (Transaction t:debitList){
            SenderTransaction t1 = new SenderTransaction();
            t1.setTransactionId(t.getTransactionId());
            t1.setReceiverName(t.getReceiverName());
            t1.setReceiverAccountNumber(t.getReceiverAccountNumber());
            t1.setReceiverBankIfsc(t.getReceiverBankIfsc());
            t1.setTransactionAmount(t.getTransactionAmount());
            t1.setTransactionDate(t.getTransactionDate());
            filteredDebitList.add(t1);
        }
        if(debitList.isEmpty()){
        throw new TransactionNotFound("No transactions found");
    }
    return filteredDebitList.stream().limit(numberOfTransactions).collect(Collectors.toList());

}
// Method to view credit details
    public List<ReceiverTransaction> fetchCreditTransactions(long accountNumber, int numberOfTransactions) throws TransactionNotFound{


        List<Transaction>  creditList = transactionRepo.findByReceiverAccountNumber(accountNumber);
        Collections.sort(creditList,(t1,t2)->t1.getTransactionDate().compareTo(t2.getTransactionDate()));
        Collections.reverse(creditList);
        List<ReceiverTransaction> filteredCreditList = new ArrayList<>();
        for (Transaction t:creditList){
            ReceiverTransaction t1 = new ReceiverTransaction();
            t1.setTransactionId(t.getTransactionId());
            t1.setSenderName(t.getSenderName());
            t1.setSenderAccountNumber(t.getSenderAccountNumber());
            t1.setSenderBankIfsc(t.getSenderBankIfsc());
            t1.setTransactionAmount(t.getTransactionAmount());
            t1.setTransactionDate(t.getTransactionDate());
            filteredCreditList.add(t1);
        }
        if(creditList.isEmpty()){
            throw new TransactionNotFound("No transactions found");
        }
        return filteredCreditList.stream().limit(numberOfTransactions).collect(Collectors.toList());
    }

    //Shows the previous transactions made
    public List<Transaction> getTransactionHistory(long accountNumber,int numberOfTransactions) throws TransactionNotFound {
        List<Transaction>  debitList = transactionRepo.findBySenderAccountNumber(accountNumber);
        List<Transaction>  creditList = transactionRepo.findByReceiverAccountNumber(accountNumber);
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(debitList);
        transactions.addAll(creditList);
        Collections.sort(transactions,(t1,t2)->t1.getTransactionDate().compareTo(t2.getTransactionDate()));
        Collections.reverse(transactions);
        if (transactions.isEmpty()){
            throw new TransactionNotFound("No transactions found");
        }
        return transactions.stream().limit(numberOfTransactions).collect(Collectors.toList());

    }

    //Shows transaction history of all accounts of the customer
    public List<Transaction> getAllAcctTransactionHistory(String name, int numberOfTransactions) throws TransactionNotFound {
        List<Transaction>  debitList = transactionRepo.findBySenderName(name);
        List<Transaction>  creditList = transactionRepo.findByReceiverName(name);
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(creditList);
        transactions.addAll(debitList);
        Collections.sort(transactions,(t1,t2)->t1.getTransactionDate().compareTo(t2.getTransactionDate()));
        Collections.reverse(transactions);
        if (transactions.isEmpty()){
            throw  new TransactionNotFound("No transactions found");
        }
        return transactions.stream().limit(numberOfTransactions).collect(Collectors.toList());

    }
}
