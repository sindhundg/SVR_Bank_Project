package com.bank.BankService.service;

import com.bank.BankService.exceptions.AccountAlreadyExists;
import com.bank.BankService.exceptions.AccountNotFound;
import com.bank.BankService.exceptions.InsufficientBalance;
import com.bank.BankService.exceptions.InvalidPin;
import com.bank.BankService.model.Transaction;
import com.bank.BankService.rabbitmqconfiguration.DataFormat;
import com.bank.BankService.model.Account;
import com.bank.BankService.repository.AccountRepo;
import org.json.simple.JSONObject;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class BankService implements IBankService{
    @Autowired
    private AccountRepo bankRepo;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange directExchange;
    @Override
    public Account createAccount(Account account) throws AccountAlreadyExists {
         Optional<Account> bopt= bankRepo.findById((double) account.getId());
        if(bopt.isPresent()){
            throw new AccountAlreadyExists("Account already exists");
        }
        else {

           Account bank1= bankRepo.save(account);
            return bank1;
        }
    }

    @Override
    public boolean deleteAccount(long accountNumber, int pin) throws AccountNotFound {
        Optional<Account> dopt = Optional.of(bankRepo.findByAccountNumberAndPin(accountNumber,pin));
        if(dopt.isEmpty())
        {
            throw  new AccountNotFound("Account does not exist");
        }
//        Account account =dopt.get();
        bankRepo.deleteByAccountNumberAndPin(accountNumber, pin);
        return true;
    }

    @Override
    public double showBalance(long accountNumber, int pin) throws AccountNotFound {
        Optional<Account> aopt = Optional.ofNullable(bankRepo.findByAccountNumberAndPin(accountNumber, pin));
        if (aopt.isEmpty()){
            throw new AccountNotFound("Account does not exist");
        }
        else {
            Account account = bankRepo.findByAccountNumberAndPin(accountNumber, pin);
            return account.getBalance();
         }
    }

    @Override
    public Account fetchCustomerAccount(long accountNumber, int pin) throws AccountNotFound {
        Optional<Account> aopt = Optional.ofNullable(bankRepo.findByAccountNumberAndPin(accountNumber, pin));
        if(aopt.isEmpty()){
            throw  new AccountNotFound("Account does not exist");
        }
        else
        {
            Account account = bankRepo.findByAccountNumberAndPin(accountNumber, pin);
            return account;
        }
    }
    @Override
    public Account fetchAccount(long accountNumber) throws AccountNotFound {
        Optional<Account> aopt = Optional.ofNullable(bankRepo.findByAccountNumber(accountNumber));
        if(aopt.isEmpty()){
            throw  new AccountNotFound("Account does not exist");
        }
        else
        {
            Account account = bankRepo.findByAccountNumber(accountNumber);
            return account;
        }
    }


    @Override
    public List<Account> fetchAllCustomerAccounts(String accountHolderName) throws AccountNotFound {
    List<Account> lacc = bankRepo.findByAccountHolderName(accountHolderName);
        if(lacc.isEmpty()){
            throw new AccountNotFound("No account found");
        }
        return bankRepo.findByAccountHolderName(accountHolderName);
    }

    @Override
    public boolean updateAccountDetails(long accountNumber, int pin, Account account) throws AccountNotFound {
        Optional<Account> accObj = Optional.ofNullable(bankRepo.findByAccountNumberAndPin(accountNumber, pin));
        if(accObj.isEmpty())
        {
            throw new AccountNotFound("Account not found");
        }
        else {
            Account existAcc = bankRepo.findByAccountNumberAndPin(accountNumber, pin);
            existAcc.setEmail(account.getEmail());
            existAcc.setPhoneNo(account.getPhoneNo());
            existAcc.setPin(account.getPin());
            bankRepo.save(existAcc);
            return true;
        }
    }

    @Override
    public boolean updateAccountEmail(long accountNumber, int pin, String email) throws AccountNotFound {
        Optional<Account> accObj = Optional.ofNullable(bankRepo.findByAccountNumberAndPin(accountNumber, pin));
        if (accObj.isEmpty()) {
            throw new AccountNotFound("Account not found");
        } else {
            Account existAcc = bankRepo.findByAccountNumberAndPin(accountNumber, pin);
            existAcc.setEmail(email);
            bankRepo.save(existAcc);
            return true;
        }
    }

    @Override
    public boolean updateAccountPhoneNo(long accountNumber, int pin, long PhoneNo) throws AccountNotFound {
            Optional<Account> accObj = Optional.ofNullable(bankRepo.findByAccountNumberAndPin(accountNumber, pin));
            if(accObj.isEmpty())
            {
                throw new AccountNotFound("Account not found");
            }
            else {
                Account existAcc = bankRepo.findByAccountNumberAndPin(accountNumber, pin);
                existAcc.setPhoneNo(PhoneNo);
                bankRepo.save(existAcc);
                return true;
            }
    }

    @Override
    public boolean updateAccountPin(long accountNumber, int pin, int newPin) throws AccountNotFound, InvalidPin {
        Optional<Account> accObj = Optional.ofNullable(bankRepo.findByAccountNumberAndPin(accountNumber, pin));
        if (accObj.isEmpty()) {
            throw new AccountNotFound("Account not found");
        }
        else if(accObj.get().getPin()==newPin){
            throw new InvalidPin("New pin is same as old pin");

        } else {
            Account existAcc = bankRepo.findByAccountNumberAndPin(accountNumber, pin);
            existAcc.setPin(newPin);
            bankRepo.save(existAcc);
            return true;
        }
    }

    @Override
    public boolean sendAmount(long accountNumber, int pin, double amount,Account reciverAccount) throws AccountNotFound, InsufficientBalance {
        Optional<Account> accObj = Optional.ofNullable(bankRepo.findByAccountNumberAndPin(accountNumber, pin));
        Optional<Account> recvaccount = Optional.ofNullable(bankRepo.findByAccountNumber(reciverAccount.getAccountNumber()));
//
        if (accObj.isEmpty())
        {
            throw new AccountNotFound("Sender account not found");
        } else if (recvaccount.isEmpty()) {
            throw new AccountNotFound("Receiver account not found");}

        else if(accObj.get().getBalance()<=0.0){
            throw  new InsufficientBalance("Insufficient Balance");
        } else if (amount>accObj.get().getBalance()) {
            throw  new InsufficientBalance("Insufficient Balance");
        } else {
            Account  senderAccount = bankRepo.findByAccountNumber(accountNumber);
            Account receiverAccount = bankRepo.findByAccountNumber(reciverAccount.getAccountNumber());
            senderAccount.setBalance(senderAccount.getBalance()-amount);
            receiverAccount.setBalance(receiverAccount.getBalance()+amount);
            bankRepo.save(receiverAccount);
            bankRepo.save(senderAccount);
            return true;
        }
    }
    public void sendTransactionData(String senderName, long senderAccountNumber,String senderIfsc,String receiverName,long receiverAccountNumber,String receiverIfsc, double amount)
    {
        Transaction t = new Transaction(senderName,senderAccountNumber,senderIfsc,receiverName,receiverAccountNumber,receiverIfsc,amount);
        DataFormat df=new DataFormat();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("Transaction",t);

        df.setJsonObject(jsonObject);
        rabbitTemplate.convertAndSend(directExchange.getName(),"transactionKey",df);

    }


}
