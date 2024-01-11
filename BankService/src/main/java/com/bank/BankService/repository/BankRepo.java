package com.bank.BankService.repository;

import com.bank.BankService.model.Bank;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepo extends MongoRepository<Bank,String> {
}
