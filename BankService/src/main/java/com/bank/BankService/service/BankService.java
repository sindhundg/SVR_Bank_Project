package com.bank.BankService.service;

import com.bank.BankService.exceptions.*;
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

@Service
public class BankService implements IBankService{
    @Autowired
    private AccountRepo accountRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange directExchange;

    //Method for creating bank account
    @Override
    public Account createAccount(Account account) throws AccountAlreadyExists {
        Optional<Account> bopt = accountRepository.findById((double) account.getId());
        List<Account> accountList = accountRepository.findAll();

        if (bopt.isPresent()) {
            throw new AccountAlreadyExists("Account already exists");
        } else  {
            for (Account a : accountList) {
                if ((a.getId() != account.getId()) && (a.getBank().getBankName().equals(account.getBank().getBankName())) && (a.getBank().getBranchName().equals(account.getBank().getBranchName())) && (a.getAccountHolderName().equals(account.getAccountHolderName()))) {
                    throw new AccountAlreadyExists("Account already exists");
                }
            }
        }
        accountRepository.save(account);
        return account;
    }

    //Method for deleting the account
    @Override
    public boolean deleteAccount(long accountNumber, int pin) throws InvalidAccountNumberOrPin {
        Optional<Account> dopt = Optional.ofNullable(accountRepository.findByAccountNumberAndPin(accountNumber,pin));
        if(dopt.isPresent())
        {
            accountRepository.deleteByAccountNumberAndPin(accountNumber, pin);
            return true;
        }
        throw  new InvalidAccountNumberOrPin("Either account number or pin is invalid");

    }
//Method for viewing the balance
    @Override
    public double showBalance(long accountNumber, int pin) throws  InvalidAccountNumberOrPin {
        Optional<Account> aopt = Optional.ofNullable(accountRepository.findByAccountNumberAndPin(accountNumber, pin));
        if (aopt.isEmpty()){
            throw new InvalidAccountNumberOrPin("Either account number or pin is invalid");
        }
        else {
            Account account = accountRepository.findByAccountNumberAndPin(accountNumber, pin);
            return account.getBalance();
         }
    }

    //Method for viewing the individual customer account
    @Override
    public Account fetchCustomerAccount(long accountNumber, int pin) throws InvalidAccountNumberOrPin {
        Optional<Account> aopt = Optional.ofNullable(accountRepository.findByAccountNumberAndPin(accountNumber, pin));
        if(aopt.isEmpty()){
            throw new InvalidAccountNumberOrPin("Either account number or pin is invalid");
        }
        else
        {
            return accountRepository.findByAccountNumberAndPin(accountNumber, pin);

        }
    }
    // Method for viewing the account using name of the customer
    @Override
    public Account fetchAccount(long accountNumber) throws AccountNotFound {
        Optional<Account> aopt = Optional.ofNullable(accountRepository.findByAccountNumber(accountNumber));
        if(aopt.isEmpty()){
            throw  new AccountNotFound("Account does not exist");
        }
        else
        {
            return accountRepository.findByAccountNumber(accountNumber);

        }
    }

//Method for viewing all the accounts of the customer using name
    @Override
    public List<Account> fetchAllCustomerAccounts(String accountHolderName) throws AccountNotFound {
    List<Account> lacc = accountRepository.findByAccountHolderName(accountHolderName);
        if(lacc.isEmpty()){
            throw new AccountNotFound("No account found");
        }
        return accountRepository.findByAccountHolderName(accountHolderName);
    }

    // Method for updating account details
    @Override
    public boolean updateAccountDetails(long accountNumber, int pin, Account account) throws  InvalidAccountNumberOrPin {
        Optional<Account> accObj = Optional.ofNullable(accountRepository.findByAccountNumberAndPin(accountNumber, pin));
        if(accObj.isEmpty())
        {
            throw new InvalidAccountNumberOrPin("Either account number or pin is invalid");
        }
        else {
            Account existAcc = accountRepository.findByAccountNumberAndPin(accountNumber, pin);
            existAcc.setEmail(account.getEmail());
            existAcc.setPhoneNo(account.getPhoneNo());
            existAcc.setPin(account.getPin());
            accountRepository.save(existAcc);
            return true;
        }
    }

    // Method to update email
    @Override
    public boolean updateAccountEmail(long accountNumber, int pin, String email) throws InvalidAccountNumberOrPin {
        Optional<Account> accObj = Optional.ofNullable(accountRepository.findByAccountNumberAndPin(accountNumber, pin));
        if (accObj.isEmpty()) {
            throw new InvalidAccountNumberOrPin("Either account number or pin is invalid");
        } else {
            Account existAcc = accountRepository.findByAccountNumberAndPin(accountNumber, pin);
            existAcc.setEmail(email);
            accountRepository.save(existAcc);
            return true;
        }
    }

    //Method to update phone number of the customer
    @Override
    public boolean updateAccountPhoneNo(long accountNumber, int pin, long PhoneNo) throws InvalidAccountNumberOrPin {
            Optional<Account> accObj = Optional.ofNullable(accountRepository.findByAccountNumberAndPin(accountNumber, pin));
            if(accObj.isEmpty())
            {
                throw new InvalidAccountNumberOrPin("Either account number or pin is invalid");
            }
            else {
                Account existAcc = accountRepository.findByAccountNumberAndPin(accountNumber, pin);
                existAcc.setPhoneNo(PhoneNo);
                accountRepository.save(existAcc);
                return true;
            }
    }

    // Method to set a new pin
    @Override
    public boolean updateAccountPin(long accountNumber, int pin, int newPin) throws  InvalidAccountNumberOrPin, InvalidPin {
        Optional<Account> accObj = Optional.ofNullable(accountRepository.findByAccountNumberAndPin(accountNumber, pin));
        if (accObj.isEmpty()) {
            throw new InvalidAccountNumberOrPin("Either account number or pin is invalid");
        }
        else if(accObj.get().getPin()==newPin){
            throw new InvalidPin("New pin is same as old pin");

        } else {
            Account existAcc = accountRepository.findByAccountNumberAndPin(accountNumber, pin);
            existAcc.setPin(newPin);
            accountRepository.save(existAcc);
            return true;
        }
    }

    // Method for sending money to other account
    @Override
    public boolean sendAmount(long accountNumber, int pin, double amount,Account reciverAccount) throws AccountNotFound, InsufficientBalance, TransactionNotAllowed {
       Optional<Account> accObj1 = Optional.ofNullable(accountRepository.findByAccountNumberAndPin(accountNumber, pin));
        Optional<Account> accObj2 = Optional.ofNullable(accountRepository.findByAccountNumber(reciverAccount.getAccountNumber()));
//
        if (accObj1.isEmpty())
        {
            throw new AccountNotFound("Sender account not found");
        }
        else if (accObj2.isEmpty())
        {
            throw new AccountNotFound("Receiver account not found");
        }
        else if(accObj2.get().getAccountNumber() == accountNumber)
        {
            throw  new TransactionNotAllowed("Transaction not allowed");
        }
        else if(accObj1.get().getBalance()<=0.0){
            throw  new InsufficientBalance("Insufficient Balance");
        } else if (amount>accObj1.get().getBalance()) {
            throw  new InsufficientBalance("Insufficient Balance");
        } else {
            Account  senderAccount = accountRepository.findByAccountNumberAndPin(accountNumber,pin);
            Account receiverAccount = accountRepository.findByAccountNumber(reciverAccount.getAccountNumber());
            senderAccount.setBalance(senderAccount.getBalance()-amount);
            receiverAccount.setBalance(receiverAccount.getBalance()+amount);
            accountRepository.save(receiverAccount);
            accountRepository.save(senderAccount);
            return true;
        }
    }
    // Method to send transaction details to transaction service through rabbitmq
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
