package com.bank.BankService.service;

import com.bank.BankService.exceptions.AccountAlreadyExists;
import com.bank.BankService.exceptions.AccountNotFound;
import com.bank.BankService.model.Account;
import com.bank.BankService.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService implements IBankService{
@Autowired
    private AccountRepo bankRepo;
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
        Account account =dopt.get();
        bankRepo.deleteById((double) account.getId());
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
    public List<Account> fetchAllCustomerAccounts(String accountHolderName) {
    List<Account> lacc = bankRepo.findByAccountHolderName(accountHolderName);
    return lacc;
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
    public boolean updateAccountPin(long accountNumber, int pin, int newPin) throws AccountNotFound {
        Optional<Account> accObj = Optional.ofNullable(bankRepo.findByAccountNumberAndPin(accountNumber, pin));
        if (accObj.isEmpty()) {
            throw new AccountNotFound("Account not found");
        } else {
            Account existAcc = bankRepo.findByAccountNumberAndPin(accountNumber, pin);
            existAcc.setPin(newPin);
            bankRepo.save(existAcc);
            return true;
        }
    }


}
