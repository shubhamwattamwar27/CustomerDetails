package com.example.democustomerdetails.service;

import com.example.democustomerdetails.domain.CustomerRequestObject;
import com.example.democustomerdetails.entity.Customer;
import com.example.democustomerdetails.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        }
        return new Customer();
    }

    @Override
    public String saveCustomer(CustomerRequestObject customerRequestObject) {
        if (customerRequestObject == null) {
            return "Customer Cannot be null";
        }
        Customer newCustomer = Customer.builder()
                .id(customerRequestObject.getId())
                .firstName(customerRequestObject.getFirstName())
                .lastName(customerRequestObject.getLastName())
                .build();
        Customer savedCustomer = customerRepository.save(newCustomer);
        if (savedCustomer != null) {
            return "Customer Saved Successfully";
        }
        return "Customer Not Saved";
    }
}
