package com.authenticate.AuthenticationService.service;

import com.authenticate.AuthenticationService.exceptions.CustomerAlreadyExists;
import com.authenticate.AuthenticationService.exceptions.CustomerNotFound;
import com.authenticate.AuthenticationService.model.Customer;
import java.util.Map;
public interface ICustomerService {
    public Customer register(Customer cust) throws CustomerAlreadyExists;
    public Map<String,String> login(String email, String password) throws CustomerNotFound;
    public boolean updateByCustId(Customer cust,int custid);
    public boolean deleteCustById(int custId);
}
