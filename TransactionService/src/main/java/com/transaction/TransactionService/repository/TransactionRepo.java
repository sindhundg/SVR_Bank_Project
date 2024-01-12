package com.transaction.TransactionService.repository;

import com.transaction.TransactionService.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends MongoRepository<Transaction, Integer> {
}
