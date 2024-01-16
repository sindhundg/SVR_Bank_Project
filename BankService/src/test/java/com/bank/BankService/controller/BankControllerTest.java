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
    private Bank bank;
    @Autowired
    private AccountRepo accountRepo;
    @BeforeEach
    public void setup(){
        bank = new Bank("HDFC123Mandya","HDFC","Mandya");
        account = new Account(1232414,2131231233,"Mathew","Mathew@gmail.com",989143434,3000,5555,bank);
        mockmvc= MockMvcBuilders.standaloneSetup(bcon).build();
    }

    @AfterEach
    public void tearDown()
    {
        bank=null;
        account=null;

    }
    @Test
    public void checkAddSuccess() throws Exception{
        when(bser.createAccount(any(Account.class))).thenReturn(account);
        mockmvc.perform(post("/bank/createAccount/bankName/branchName")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(account)))
                .andExpect(status().isCreated());
    }
    public String jsonToString(Account a) throws JsonProcessingException{
        String result;
        ObjectMapper objmpr=new ObjectMapper();
        String jsonContent =objmpr.writeValueAsString(a);
        return jsonContent;
    }
    @Test
    public void checkDeleteByAccount() throws Exception{
        when(bser.deleteAccount(anyLong(),anyInt())).thenReturn(true);
        mockmvc.perform(delete("/bank/deleteAccount/9876543210/1235")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
        verify(bser,times(1)).deleteAccount(anyLong(),anyInt());


    }
}




