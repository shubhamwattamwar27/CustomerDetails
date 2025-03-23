package com.example.democustomerdetails.service;

import com.example.democustomerdetails.domain.CustomerRequestObject;
import com.example.democustomerdetails.entity.Customer;
import com.example.democustomerdetails.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    public static final String TEST_F_NAME = "TestFName";
    public static final String TEST_LAST_NAME = "TestLastName";

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;


    @Test
    void shouldGetCustomerById() {
        // Given
        Long customerId = 1L;
        Customer customer = new Customer(customerId, TEST_F_NAME, TEST_LAST_NAME, "27/08/2005");
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        // When
        Customer result = customerService.getCustomerById(customerId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(customerId);
        assertThat(result.getFirstName()).isEqualTo(TEST_F_NAME);
        assertThat(result.getLastName()).isEqualTo(TEST_LAST_NAME);
    }

    @Test
    void shouldNotFoundGetCustomerById() {
        // Given
        Long customerId = 1L;
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        Customer result = customerService.getCustomerById(customerId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(new Customer());
    }

    @Test
    void shouldSaveCustomer() {
        // Given
        CustomerRequestObject customerRequestObject = new CustomerRequestObject(TEST_F_NAME, TEST_LAST_NAME, "27/08/2005");
        Customer customer = new Customer(null, TEST_F_NAME, TEST_LAST_NAME, "27/08/2005");
        Customer savedCustomer = new Customer(2L, TEST_F_NAME, TEST_LAST_NAME, "27/08/2005");
        when(customerRepository.save(customer)).thenReturn(savedCustomer);

        // When
        String result = customerService.saveCustomer(customerRequestObject);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("Customer Saved Successfully");
    }

    @Test
    void shouldNotSaveCustomer() {
        // Given
        CustomerRequestObject customerRequestObject = new CustomerRequestObject(TEST_F_NAME, TEST_LAST_NAME, "27/08/2005");
        Customer customer = new Customer(null, TEST_F_NAME, TEST_LAST_NAME, "27/08/2005");
        when(customerRepository.save(customer)).thenReturn(null);

        // When
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.saveCustomer(customerRequestObject);
        });

        // Then
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Customer Not Saved");
    }

    @Test
    void shouldNotSaveCustomer_customerCannotBeNull() {

        // When
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            customerService.saveCustomer(null);
        });

        // Then
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Customer cannot be null");
    }
}
