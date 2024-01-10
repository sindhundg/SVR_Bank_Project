package com.authenticate.AuthenticationService.service;

import com.authenticate.AuthenticationService.exceptions.CustomerAlreadyExists;
import com.authenticate.AuthenticationService.exceptions.CustomerNotFound;
import com.authenticate.AuthenticationService.model.Customer;
import com.authenticate.AuthenticationService.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.CustomMetric;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private CustomerRepo crepo;


    @Override
    public Customer register(Customer cust) throws CustomerAlreadyExists {
        Optional<Customer> custObj = crepo.findById(cust.getCustomerid());
        if(custObj.isEmpty())
        {
            Customer cc = crepo.save(cust);
            return cc;
        }
        else
        {
            throw new CustomerAlreadyExists("Customer already exists");
        }
    }

    @Override
    public Map<String, String> login(String email, String password) throws CustomerNotFound {
        Map<String,String> token=new HashMap<String,String>();
        Optional<Customer> custObj = Optional.ofNullable(crepo.findByEmailAndPassword(email, password));
        if(custObj.isEmpty())
        {
            throw new CustomerNotFound("Customer not found");
        }
        else {
            token = getJwtToken(email);
            return token;
        }

    }

    @Override
    public boolean updateByCustId(Customer cust, int custid) {
        Customer existingCustomer = crepo.findById(custid).get();
        existingCustomer.setCustomerName(cust.getCustomerName());
        existingCustomer.setAge(cust.getAge());
        existingCustomer.setEmail(cust.getEmail());
        existingCustomer.setPassword(cust.getPassword());
        existingCustomer.setCity(cust.getCity());
        existingCustomer.setPhoneNo(cust.getPhoneNo());
        crepo.save(existingCustomer);
        return true;
    }

    public Map<String,String> getJwtToken(String email){
        Map<String,String> tok=new HashMap<String,String>();
        String jwtToken= Jwts.builder().setSubject(email).setIssuedAt( new Date(0))
                .signWith(SignatureAlgorithm.HS256,"secretkey").compact();
        tok.put("token",jwtToken);
        return tok;

    }
}
