package com.transaction.TransactionService.service;

import com.transaction.TransactionService.exceptions.TransactionNotFound;
import com.transaction.TransactionService.model.Transaction;
import com.transaction.TransactionService.repository.TransactionRepo;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import com.transaction.TransactionService.rabbitmqconfiguration.DataFormat;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


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
public List<Transaction> fetchDebitTransactions(long accountNumber,int numberOfTransactions) throws TransactionNotFound {
    List<Transaction> topt = trepo.findBySenderAccountNumber(accountNumber);
    if (topt.isEmpty()){
        throw  new TransactionNotFound("No debit transactions found");
    }
        Query query = new Query();
        query.addCriteria(where("senderAccountNumber").is(accountNumber)).limit(numberOfTransactions);
        query.with(Sort.by(Sort.Direction.DESC,"transactionDate"));
        return  mongoTemplate.find(query,Transaction.class);

}
    public List<Transaction> fetchCreditTransactions(long accountNumber,int numberOfTransactions) throws TransactionNotFound{
        List<Transaction> topt = trepo.findByReceiverAccountNumber(accountNumber);
        if (topt.isEmpty()){
            throw  new TransactionNotFound("No credit transactions found");
        }
        Query query = new Query();
        query.addCriteria(where("receiverAccountNumber").is(accountNumber)).limit(numberOfTransactions);
        query.with(Sort.by(Sort.Direction.DESC,"transactionDate"));
        return mongoTemplate.find(query,Transaction.class);

    }
    public List<Transaction> getTransactionHistory(long accountNumber) throws TransactionNotFound {
//        List<Transaction>  debitList = trepo.findBySenderAccountNumber(accountNumber);
//        List<Transaction>  creditList = trepo.findByReceiverAccountNumber(accountNumber);
        List<Transaction> transactions = new ArrayList<>();

        MatchOperation dlist = Aggregation.match(new Criteria("senderAccountNumber").is(accountNumber));
        MatchOperation clist = Aggregation.match(new Criteria("receiverAccountNumber").is(accountNumber));
        SortOperation sortByDate = sort(Sort.by(Sort.Direction.DESC, "transactionDate"));
        Aggregation aggregation = Aggregation.newAggregation(dlist,sortByDate);
        Aggregation aggregation1 = Aggregation.newAggregation(clist,sortByDate);
        List<Transaction> result = mongoTemplate.aggregate(aggregation, "transaction", Transaction.class).getMappedResults();
        List<Transaction> result1 = mongoTemplate.aggregate(aggregation1, "transaction", Transaction.class).getMappedResults();
        transactions.addAll(result1);
        transactions.addAll(result);


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

        if (transactions.isEmpty()){
            throw  new TransactionNotFound("No transactions found");
        }
        return  transactions;

    }
}
