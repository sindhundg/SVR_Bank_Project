package com.bank.BankService.service;

import com.bank.BankService.exceptions.AccountAlreadyExists;
import com.bank.BankService.model.Account;
import com.bank.BankService.model.Bank;
import com.bank.BankService.repository.AccountRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BankServiceTest {
    private Account account;
    private Bank bank;
    @Mock
    private AccountRepo accountRepo;

    @InjectMocks
    private BankService bankService;

    @BeforeEach
    public void setup()
    {
        bank = new Bank("HDFC123Mandya", "HDFC", "Mandya");
        account = new Account(123414, 2131231233, "Mathew","Mathew@gmail.com", 989143434, 3000,5555,bank);

    }

    @AfterEach
    public void tearDown(){
        bank = null;
        account= null;
        accountRepo.deleteAll();
    }

    @Test
    public void checkCreateAccountSuccessful() throws AccountAlreadyExists{
        when(accountRepo.findByAccountNumber(account.getAccountNumber())).thenReturn(null);
        when(accountRepo.save(account)).thenReturn(account);
        assertEquals(account,bankService.createAccount(account));


    }

}
