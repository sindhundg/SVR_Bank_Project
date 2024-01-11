package com.bank.BankService.repository;

import com.bank.BankService.model.Account;
import com.bank.BankService.model.Bank;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepo extends MongoRepository<Bank,String> {
    public List<Bank> findByAccountHolderName(String accountHolderName);
}
