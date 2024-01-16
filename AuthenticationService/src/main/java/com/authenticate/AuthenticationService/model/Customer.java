package com.authenticate.AuthenticationService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Customer {
    @Id
    private int customerId;
    private String customerName;
    private String email;
    private String password;
    private int age;
    private String city;
    private long phoneNo;

    public Customer() {
    }

    public Customer(int customerId, String customerName, String email, String password, int age, String city, long phoneNo) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
        this.password = password;
        this.age = age;
        this.city = city;
        this.phoneNo = phoneNo;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                ", phoneNo=" + phoneNo +
                '}';
    }
}
