package com.example.democustomerdetails.service;

import com.example.democustomerdetails.domain.CustomerRequestObject;
import com.example.democustomerdetails.entity.Customer;

public interface CustomerService {

    Customer getCustomerById(Long id);

    String saveCustomer(CustomerRequestObject customer);
}
