package com.transaction.TransactionService.repository;

import com.transaction.TransactionService.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends MongoRepository<Transaction, Long> {
    public List<Transaction> findBySenderAccountNumber(long senderAccountNumber);
    public List<Transaction> findByReceiverAccountNumber(long receiverAccountNumber);
    public List<Transaction> findBySenderName(String senderName);
    public List<Transaction> findByReceiverName(String receiverName);

}
