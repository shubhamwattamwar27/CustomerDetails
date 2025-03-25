package com.example.democustomerdetails.service;

import com.example.democustomerdetails.domain.CustomerRequestObject;
import com.example.democustomerdetails.entity.Customer;
import com.example.democustomerdetails.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class CustomerServiceImplIntegrationTest {

    public static final String FIRST_NAME = "Shubham";
    public static final String LAST_NAME = "Wattamwar";
    public static final String DOB = "27/08/2005";
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    void shouldSaveCustomer() {
        CustomerRequestObject request = new CustomerRequestObject();
        request.setFirstName(FIRST_NAME);
        request.setLastName(LAST_NAME);
        request.setDob(DOB);

        String response = customerService.saveCustomer(request);

        assertThat(response).isNotBlank();
        assertThat(response).isEqualTo("Customer Saved Successfully");
        assertThat(1).isEqualTo(customerRepository.count());
    }

    @Test
    void shouldGetCustomerById() {
        Customer customer = new Customer();
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
        customer.setDob(DOB);

        Customer savedCustomer = customerRepository.save(customer);

        Customer retrievedCustomer = customerService.getCustomerById(savedCustomer.getId());

        assertThat(retrievedCustomer).isNotNull();
        assertThat(FIRST_NAME).isEqualTo(retrievedCustomer.getFirstName());
    }

    @Test
    void shouldNotGetCustomerById_NotFound() {
        Long invalidId = 12456L;

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            customerService.getCustomerById(invalidId);
        });

        assertThat("400 Customer id not valid").isEqualTo(exception.getMessage());
    }
}
