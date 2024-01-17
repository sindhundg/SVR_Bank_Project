package com.authenticate.AuthenticationService.repository;

import com.authenticate.AuthenticationService.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Integer> {
  //Custom repository methods to find customer by email and password
  public Customer findByEmailAndPassword(String email, String password);
}
