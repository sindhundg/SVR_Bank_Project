package com.authenticate.AuthenticationService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Customer {
    @Id
    private int customerid;
    private int age;
    private String customerName;
    private String password;
    private String email;
    private String city;
    private long phoneNo;

    public Customer() {
    }

    public Customer(int customerid, String customerName, String password, String email, String city, long phoneNo, int age) {
        this.customerid = customerid;
        this.customerName = customerName;
        this.password = password;
        this.email = email;
        this.city = city;
        this.phoneNo = phoneNo;
        this.age = age;
    }



    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerid=" + customerid +
                ", age=" + age +
                ", customerName='" + customerName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", phoneNo=" + phoneNo +
                '}';
    }
}
