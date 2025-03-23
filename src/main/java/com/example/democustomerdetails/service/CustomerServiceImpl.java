package com.example.democustomerdetails.service;

import com.example.democustomerdetails.domain.CustomerRequestObject;
import com.example.democustomerdetails.entity.Customer;
import com.example.democustomerdetails.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

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
        throw new HttpClientErrorException(BAD_REQUEST, "Customer id not valid");
    }

    @Override
    public String saveCustomer(CustomerRequestObject customerRequestObject) {
        if (customerRequestObject == null) {
            throw new HttpClientErrorException(BAD_REQUEST, "Customer cannot be null");
        }
        Customer newCustomer = getCustomer(customerRequestObject);
        Customer savedCustomer = customerRepository.save(newCustomer);
        if (savedCustomer == null) {
            throw new RuntimeException("Customer Not Saved");
        }
        return "Customer Saved Successfully";
    }

    private static Customer getCustomer(CustomerRequestObject customerRequestObject) {
        return Customer.builder()
                .firstName(customerRequestObject.getFirstName())
                .lastName(customerRequestObject.getLastName())
                .dob(customerRequestObject.getDob())
                .build();
    }
}
