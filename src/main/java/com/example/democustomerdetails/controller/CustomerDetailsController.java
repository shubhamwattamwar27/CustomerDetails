package com.example.democustomerdetails.controller;

import com.example.democustomerdetails.domain.CustomerRequestObject;
import com.example.democustomerdetails.entity.Customer;
import com.example.democustomerdetails.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customerDetails")
public class CustomerDetailsController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer-test")
    public String getCustomerServiceTest() {
        return "customer-test";
    }

    @GetMapping("/customer")
    public Customer getCustomer(@Positive(message = "Customer ID should be positive") @RequestParam Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/customer")
    public String saveCustomer(@Valid @RequestBody CustomerRequestObject customer) {
        return customerService.saveCustomer(customer);
    }

}
