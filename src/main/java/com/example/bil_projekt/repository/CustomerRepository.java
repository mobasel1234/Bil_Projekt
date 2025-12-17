package com.example.bil_projekt.repository;


import com.example.bil_projekt.model.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAll();

    Customer findByEmail(String email);

    int create(Customer customer);


}

