package com.authenticate.AuthenticationService.repository;

import com.authenticate.AuthenticationService.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {
    private Customer customer;
    @Autowired
    private CustomerRepo customerRepo;
    @BeforeEach
    public void setup(){
    customer = new Customer(101,"Robert","Robert@gmail.com","abc@123",22,"Bengaluru",231231334);
    }

    @AfterEach
    public void tearDown()
    {
        customer=null;
        customerRepo.deleteAll();
    }
    @Test
    public void checkSuccessfulRegistration()
    {
        customerRepo.save(customer);
        Customer customer1 = customerRepo.findById(customer.getCustomerId()).get();
        assertNotNull(customer1);
        assertEquals(customer.getCustomerId(),customer1.getCustomerId());
    }

}
