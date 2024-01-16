package com.bank.BankService.repository;
import com.bank.BankService.repository.AccountRepo;
import com.bank.BankService.model.Account;
import com.bank.BankService.model.Bank;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class AccountRepositoryTest {
    private Account account;
    private Bank bank;
    @Autowired
    private AccountRepo accountRepo;
    @BeforeEach
    public void setup(){
        bank = new Bank("HDFC123Mandya","HDFC","Mandya");
        account = new Account(1232414,2131231233,"Mathew","Mathew@gmail.com",989143434,3000,5555,bank);
    }

    @AfterEach
    public void tearDown()
    {
        bank=null;
        account=null;
        accountRepo.deleteAll();
    }
    @Test
    void contextLoads(){

    }
    @Test
    public void checkSuccessfulAccountCreation(){
        accountRepo.save(account);
        Account account1 = accountRepo.findByAccountNumber(account.getAccountNumber());
        assertNotNull(account1);
        assertEquals(account.getAccountNumber(),account1.getAccountNumber());
    }
    @Test
    public void checkDeletionSuccessfull(){
        accountRepo.save(account);
        accountRepo.deleteByAccountNumberAndPin(account.getAccountNumber(),account.getPin());
        Optional<Account> a1= Optional.ofNullable(accountRepo.findByAccountNumberAndPin(account.getAccountNumber(), account.getPin()));
        assertEquals(Optional.empty(),a1);

}
}