package com.bank.BankService.repository;

import com.bank.BankService.model.Account;
import com.bank.BankService.model.Bank;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends MongoRepository<Account,Double> {
    public List<Account> findByAccountHolderName(String accountHolderName);

}
