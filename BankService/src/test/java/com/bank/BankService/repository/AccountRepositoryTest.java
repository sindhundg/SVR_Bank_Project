package com.bank.BankService.repository;

import com.bank.BankService.model.Account;
import com.bank.BankService.model.Bank;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
    public void checkSuccessfulAccountCreation(){
        accountRepo.save(account);
        Account account1 = accountRepo.findByAccountNumber(account.getAccountNumber());
        assertNotNull(account1);
        assertEquals(account.getAccountNumber(),account1.getAccountNumber());
    }


    @Test
    public void checkSuccessfulDeletion()
    {
        accountRepo.save(account);
        accountRepo.deleteByAccountNumberAndPin(account.getAccountNumber(), account.getPin());
        Optional<Account> accobj = Optional.ofNullable(accountRepo.findByAccountNumberAndPin(account.getAccountNumber(), account.getPin()));
        assertEquals(Optional.empty(), accobj);
    }

    @Test
    public void checkFetchCustomerAccount()
    {
        accountRepo.save(account);
        Account accobj = accountRepo.findByAccountNumberAndPin(account.getAccountNumber(), account.getPin());
        assertEquals(account.getPin(), accobj.getPin());

    }

    @Test
    public void checkFetchAllAccounts()
    {
        Bank bank2 = new Bank("HDFC123Mysore","HDFC","Mysuru");
        Account account2 = new Account(1232415,2131231234,"Marie","Marie@gmail.com",989144534,5000,5775,bank2);
        accountRepo.save(account);
        accountRepo.save(account2);
        List<Account> accList = accountRepo.findAll();
        assertEquals(2, accList.size());
        assertEquals(5775, accList.get(1).getPin());
    }

    @Test
    public void checkFetchAllAccountOfCustomer()
    {
        Bank bank2 = new Bank("SBI123Mandya","SBI","Mandya");
        Account account2 = new Account(1232434,2131461233,"Mathew","Mathew@gmail.com",989143434,4000,6555,bank2);
        accountRepo.save(account);
        accountRepo.save(account2);
        List<Account> accList = accountRepo.findByAccountHolderName("Mathew");
        assertEquals(2, accList.size());
        assertEquals(1232434, accList.get(1).getId());
}


}
