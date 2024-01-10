package com.authenticate.AuthenticationService.repository;

import com.authenticate.AuthenticationService.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Integer> {
  public Customer findByEmailAndPassword(String email, String password);
}
