package com.transaction.TransactionService.service;

import com.transaction.TransactionService.exceptions.TransactionNotFound;
import com.transaction.TransactionService.model.ReceiverTransaction;
import com.transaction.TransactionService.model.SenderTransaction;
import com.transaction.TransactionService.model.Transaction;
import com.transaction.TransactionService.repository.TransactionRepo;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import com.transaction.TransactionService.rabbitmqconfiguration.DataFormat;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static org.springframework.data.mongodb.core.query.Criteria.where;



@Service
public class TransactionService {
    @Autowired
    private TransactionRepo trepo;
    @Autowired
    private  MongoTemplate mongoTemplate;

    @RabbitListener(queues = "TransactionQueue")
    public void receiveDataFromProducer(DataFormat df) throws JsonProcessingException {
        ObjectMapper objmap = new ObjectMapper();
        Object nmsg1 = df.getJsonObject().get("Transaction");
        String jsonString = objmap.writeValueAsString(nmsg1);
        Transaction transaction = objmap.readValue(jsonString, Transaction.class);
        trepo.save(transaction);
        System.out.println(df.getJsonObject().toJSONString());
    }
public List<SenderTransaction> fetchDebitTransactions(long accountNumber,int numberOfTransactions) throws TransactionNotFound {
        Query query = new Query();
        query.addCriteria(where("senderAccountNumber").is(accountNumber)).limit(numberOfTransactions);
        query.with(Sort.by(Sort.Direction.DESC,"transactionDate"));
        List<Transaction> debitList =   mongoTemplate.find(query,Transaction.class);
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
    return filteredDebitList;

}
    public List<ReceiverTransaction> fetchCreditTransactions(long accountNumber, int numberOfTransactions) throws TransactionNotFound{

        Query query = new Query();
        query.addCriteria(where("receiverAccountNumber").is(accountNumber)).limit(numberOfTransactions);
        query.with(Sort.by(Sort.Direction.DESC,"transactionDate"));
        List<Transaction> creditList = mongoTemplate.find(query,Transaction.class);
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
        return filteredCreditList;
    }
    public List<Transaction> getTransactionHistory(long accountNumber) throws TransactionNotFound {
        List<Transaction>  debitList = trepo.findBySenderAccountNumber(accountNumber);
        List<Transaction>  creditList = trepo.findByReceiverAccountNumber(accountNumber);
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(debitList);
        transactions.addAll(creditList);
        Collections.sort(transactions,(t1,t2)->t1.getTransactionDate().compareTo(t2.getTransactionDate()));
        Collections.reverse(transactions);
        if (transactions.isEmpty()){
            throw new TransactionNotFound("No transactions found");
        }
        return  transactions;
    }
    public List<Transaction> getAllAcctTransactionHistory(String name) throws TransactionNotFound {
        List<Transaction>  debitList = trepo.findBySenderName(name);
        List<Transaction>  creditList = trepo.findByReceiverName(name);
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(creditList);
        transactions.addAll(debitList);
        Collections.sort(transactions,(t1,t2)->t1.getTransactionDate().compareTo(t2.getTransactionDate()));
        Collections.reverse(transactions);
        if (transactions.isEmpty()){
            throw  new TransactionNotFound("No transactions found");
        }
        return  transactions;

    }
}
