package com.bank.BankService.controller;

import com.bank.BankService.model.Account;
import com.bank.BankService.model.Bank;
import com.bank.BankService.repository.AccountRepo;
import com.bank.BankService.service.BankService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

//import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@ExtendWith(MockitoExtension.class)

public class BankControllerTest {
    @Autowired
    private MockMvc mockmvc;
    @Mock
    private BankService bser;
    @InjectMocks
    private BankController bcon;

    private Account account;
    private Account account2;
    private List<Account> acclist;
    private Bank bank;
    private Bank bank2;
    @Autowired
    private AccountRepo accountRepo;

    @BeforeEach
    public void setup() {
        bank = new Bank("HDFC123Mandya", "HDFC", "Mandya");
        account = new Account(1232414, 2131231233, "Mathew", "Mathew@gmail.com", 989143434, 3000, 5555, bank);
        mockmvc = MockMvcBuilders.standaloneSetup(bcon).build();

        bank2 = new Bank("HDFC123Mandya", "HDFC", "Mandya");
        account2 = new Account(1232414, 2131231233, "Mathew", "Mathew@gmail.com", 989143434, 3000, 5555, bank);
        mockmvc = MockMvcBuilders.standaloneSetup(bcon).build();
        acclist = new ArrayList<>();


    }

    @AfterEach
    public void tearDown() {
        bank = null;
        account = null;
        bank2 = null;
        account2 = null;


    }

    @Test
    public void checkAddSuccess() throws Exception {
        when(bser.createAccount(any(Account.class))).thenReturn(account);
        mockmvc.perform(post("/bank/createAccount/bankName/branchName")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(account)))
                .andExpect(status().isCreated());
    }

    public String jsonToString(Account a) throws JsonProcessingException {
        String result;
        ObjectMapper objmpr = new ObjectMapper();
        String jsonContent = objmpr.writeValueAsString(a);
        return jsonContent;
    }

    @Test
    public void checkDeleteByAccount() throws Exception {
        when(bser.deleteAccount(anyLong(), anyInt())).thenReturn(true);
        mockmvc.perform(delete("/bank/deleteAccount/2131231233/5555")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bser, times(1)).deleteAccount(anyLong(), anyInt());
    }

    @Test
    public void checkShowBalance() throws Exception {
        when(bser.showBalance(anyLong(), anyInt())).thenReturn(3000.0);
        mockmvc.perform(get("/bank/showBalance/2131231233/5555")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bser, times(1)).showBalance(anyLong(), anyInt());
    }

    @Test
    public void checkGetAccountsuccesfull() throws Exception {
        when(bser.fetchCustomerAccount(anyLong(), anyInt())).thenReturn(account);
        mockmvc.perform(get("/bank/getAccount/2131231233/5555")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bser, times(1)).fetchCustomerAccount(anyLong(), anyInt());

    }

    @Test
    public void checkGetName() throws Exception {
        acclist.add(account);
        acclist.add(account2);
        when(bser.fetchAllCustomerAccounts(anyString())).thenReturn(acclist);
        mockmvc.perform(get("/bank/getAccounts/Mathew")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bser, times(1)).fetchAllCustomerAccounts(anyString());
    }

    @Test
    public void checkUpdateSuccessful() throws Exception {
       // when(bser.updateAccountDetails(2131231233, 5555, account)).thenReturn(true);
        mockmvc.perform(patch("/bank/updateAccount/2131231233/5555")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(account)))
                .andExpect(status().isOk());
        verify(bser, times(1)).updateAccountDetails(anyLong(), anyInt(), any());

    }
@Test
    public void checkUpdateEmailSuccess() throws Exception{
       when(bser.updateAccountEmail(anyLong(),anyInt(),anyString())).thenReturn(true);
    mockmvc.perform(put("/bank/updateAccountEmail/2131231233/5555/Mathew@gmail.com")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    verify(bser, times(1)).updateAccountEmail(anyLong(), anyInt(),anyString());
}
@Test
    public void checkUpdatePhonrSuccess() throws Exception{
        when(bser.updateAccountPhoneNo(anyLong(),anyInt(),anyLong())).thenReturn(true);
        mockmvc.perform(put("/bank/updateAccountPhoneNo/2131231233/5555/989143434")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bser,times(1)).updateAccountPhoneNo(anyLong(),anyInt(),anyLong());

}
@Test
    public void checkUpdatePinSuccess() throws Exception{
        when(bser.updateAccountPin(anyLong(),anyInt(),anyInt())).thenReturn(true);
        mockmvc.perform(put("/bank/updateAccountPin/2131231233/5555/5555")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bser,times(1)).updateAccountPin(anyLong(),anyInt(),anyInt());

}

}




