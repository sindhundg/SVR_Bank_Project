package com.authenticate.AuthenticationService.service;

import com.authenticate.AuthenticationService.exceptions.CustomerAlreadyExists;
import com.authenticate.AuthenticationService.exceptions.CustomerNotFound;
import com.authenticate.AuthenticationService.model.Customer;
import java.util.Map;
public interface ICustomerService {
    public Customer register(Customer customer) throws CustomerAlreadyExists;
    public Map<String,String> login(String email, String password) throws CustomerNotFound;
    public boolean updateByCustId(Customer customer,int customerId);
    public boolean deleteCustById(int customerId);
}
