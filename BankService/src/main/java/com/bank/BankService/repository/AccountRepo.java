package com.bank.BankService.repository;

import com.bank.BankService.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
// Custom repository methods
@Repository
public interface AccountRepo extends MongoRepository<Account,Integer> {
    public List<Account> findByAccountHolderName(String accountHolderName);
    public Account findByAccountNumber(long accountNumber);
    public Account findByAccountNumberAndPin(long accountNumber, int pin);
    public void deleteByAccountNumberAndPin(long accountNumber, int pin);



}
