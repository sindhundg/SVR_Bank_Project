package com.transaction.TransactionService.service;

import com.transaction.TransactionService.exceptions.TransactionNotFound;
import com.transaction.TransactionService.model.Transaction;
import com.transaction.TransactionService.repository.TransactionRepo;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import com.transaction.TransactionService.rabbitmqconfiguration.DataFormat;

import java.util.List;
import java.util.Optional;

@Service
public class TransactService {
    @Autowired
    private TransactionRepo trepo;
    @RabbitListener(queues = "TransactionQueue")
    public void receiveDataFromProducer(DataFormat df) throws JsonProcessingException {
        ObjectMapper objmap = new ObjectMapper();
        Object nmsg1 = df.getJsonObject().get("Transaction");
        String jsonString = objmap.writeValueAsString(nmsg1);
        Transaction transaction = objmap.readValue(jsonString, Transaction.class);
        trepo.save(transaction);
        System.out.println(df.getJsonObject().toJSONString());
    }
public List<Transaction> fetchDebitTransactions(long accountNumber) throws TransactionNotFound {
    List<Transaction> topt = trepo.findBySenderAccountNumber(accountNumber);
    if (topt.isEmpty()){
        throw  new TransactionNotFound("No debit transactions found");
    }
       List<Transaction> tlist = (List<Transaction>) trepo.findBySenderAccountNumber(accountNumber);
        return tlist;
}
    public List<Transaction> fetchCreditTransactions(long accountNumber) throws TransactionNotFound{
        List<Transaction> topt = trepo.findByReceiverAccountNumber(accountNumber);
        if (topt.isEmpty()){
            throw  new TransactionNotFound("No credit transactions found");
        }
        List<Transaction> tlist = (List<Transaction>) trepo.findByReceiverAccountNumber(accountNumber);
        return tlist;
    }

}
