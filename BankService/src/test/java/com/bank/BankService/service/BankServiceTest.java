package com.bank.BankService.service;

import com.bank.BankService.exceptions.*;
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
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BankServiceTest {
    private Account account;
    private Account account2;
    private Bank bank;
    private Bank bank2;
    private List<Account> accList;
    @Mock
    private AccountRepo accountRepo;

    @InjectMocks
    private BankService bankService;

    @BeforeEach
    public void setup()
    {
        bank = new Bank("HDFC123Mandya", "HDFC", "Mandya");
        account = new Account(123414, 2131231233, "Mathew","Mathew@gmail.com", 989143434, 3000,5555,bank);
//        accountRepo.save(account);
        bank2 = new Bank("SBI123Mandya", "SBI", "Mandya");
        account2 = new Account(123123, 2131231234, "Mathew","Mathew@gmail.com", 989143434, 4000,8888,bank2);
        accList = new ArrayList<>();
    }

    @AfterEach
    public void tearDown(){
        bank = null;
        account= null;
        bank2 = null;
        account2= null;
        accList = null;
        accountRepo.deleteAll();
    }

    @Test
    public void checkCreateAccountSuccessful() throws AccountAlreadyExists{
        when(accountRepo.findByAccountNumber(account.getAccountNumber())).thenReturn(null);
        when(accountRepo.save(account)).thenReturn(account);
        assertEquals(account,bankService.createAccount(account));

    }
    @Test
    public void checkCreateAccountFailure() {
        when(accountRepo.findById((double) account.getId())).thenReturn(Optional.ofNullable(account));
        assertThrows(AccountAlreadyExists.class,()-> bankService.createAccount(account));
    }

    @Test
    public void checkDeleteAccountSuccess() throws InvalidAccountNumberOrPin {
     when(accountRepo.findByAccountNumberAndPin(account.getAccountNumber(),account.getPin())).thenReturn(account);
     assertTrue(bankService.deleteAccount(account.getAccountNumber(), account.getPin()));
    }

    @Test
    public void checkShowBalanceSuccess() throws InvalidAccountNumberOrPin {
        when(accountRepo.findByAccountNumberAndPin(account.getAccountNumber(), account.getPin())).thenReturn(account);
        assertEquals(3000.0, bankService.showBalance(account.getAccountNumber(), account.getPin()));
    }

    @Test
    public void checkFetchCustomerAccount() throws InvalidAccountNumberOrPin {
        when(accountRepo.findByAccountNumberAndPin(account.getAccountNumber(), account.getPin())).thenReturn(account);
        assertEquals(account, bankService.fetchCustomerAccount(account.getAccountNumber(), account.getPin()));
    }

    @Test
    public void checkFetchOneAccount() throws AccountNotFound {
        when(accountRepo.findByAccountNumber(account.getAccountNumber())).thenReturn(account);
        assertEquals(account, bankService.fetchAccount(account.getAccountNumber()));

    }

    @Test
    public void checkFetchAllAccountsSuccess() throws AccountNotFound {
        accList.add(account);
        accList.add(account2);
        when(accountRepo.findByAccountHolderName(account.getAccountHolderName())).thenReturn(accList);
        List<Account> acclist2 = bankService.fetchAllCustomerAccounts("Mathew");
        assertEquals(2, acclist2.size());
        assertEquals(5555, acclist2.get(0).getPin());
    }

    @Test
    public void checkUpdateAccountSuccess() throws InvalidAccountNumberOrPin {
        when(accountRepo.findByAccountNumberAndPin(account.getAccountNumber(), account.getPin())).thenReturn(account);
        bankService.updateAccountDetails(account.getAccountNumber(), account.getPin(), account2);
        assertEquals(8888, account.getPin());
    }

    @Test
    public void checkUpdateAccountEmailSuccess() throws InvalidAccountNumberOrPin {
        when(accountRepo.findByAccountNumberAndPin(account.getAccountNumber(), account.getPin())).thenReturn(account);
        bankService.updateAccountEmail(account.getAccountNumber(), account.getPin(), "mm2@g.com");
        assertEquals("mm2@g.com", account.getEmail());
    }

    @Test
    public void checkUpdatePhoneNoSuccess() throws InvalidAccountNumberOrPin {
        when(accountRepo.findByAccountNumberAndPin(account.getAccountNumber(), account.getPin())).thenReturn(account);
        bankService.updateAccountPhoneNo(account.getAccountNumber(), account.getPin(), 456378987);
        assertEquals(456378987, account.getPhoneNo());
    }

    @Test
    public void checkUpdatePinSuccess() throws InvalidAccountNumberOrPin, InvalidPin {
        when(accountRepo.findByAccountNumberAndPin(account.getAccountNumber(), account.getPin())).thenReturn(account);
        bankService.updateAccountPin(account.getAccountNumber(), account.getPin(), 4545);
        assertEquals(4545, account.getPin());
    }

    @Test
    public void checkSendAmountSuccess() throws TransactionNotAllowed, InsufficientBalance, AccountNotFound {
        when(accountRepo.findByAccountNumberAndPin(account.getAccountNumber(), account.getPin())).thenReturn(account);
        when(accountRepo.findByAccountNumber(account2.getAccountNumber())).thenReturn(account2);
        bankService.sendAmount(account.getAccountNumber(), account.getPin(), 500.0, account2);
        assertEquals(2500.0, account.getBalance());
        assertEquals(4500.0, account2.getBalance());
    }
}
