package com.authenticate.AuthenticationService.controller;

import com.authenticate.AuthenticationService.exceptions.CustomerAlreadyExists;
import com.authenticate.AuthenticationService.exceptions.CustomerNotFound;
import com.authenticate.AuthenticationService.model.Customer;
import com.authenticate.AuthenticationService.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("bank")
public class CustomerController {
    @Autowired
    private CustomerService custService;

    @PostMapping("register")
    public ResponseEntity<?> registerCustomer(@RequestBody Customer customer) throws CustomerAlreadyExists, CustomerNotFound {
        try {
            custService.register(customer);
            return new ResponseEntity<>("customer registration successfull", HttpStatus.CREATED);
        } catch (CustomerAlreadyExists cx) {
            throw new CustomerAlreadyExists("customer already exists");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("login/{email}/{password}")
    public ResponseEntity<?> loginCustomer(@PathVariable String email, @PathVariable String password) throws CustomerNotFound {
        try {
            Map<String, String> token = custService.login(email, password);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (CustomerNotFound ex) {
            throw new CustomerNotFound("Invalid login credentials");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updateCustomerDetails/{customerId}")
    public ResponseEntity<?> updateCustomerdDetails(@RequestBody Customer cust, @PathVariable int customerId) {
        try {
            custService.updateByCustId(cust, customerId);
            return new ResponseEntity<>("Customer details updted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


