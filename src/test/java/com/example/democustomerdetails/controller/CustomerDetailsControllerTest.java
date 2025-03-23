package com.example.democustomerdetails.controller;

import com.example.democustomerdetails.domain.CustomerRequestObject;
import com.example.democustomerdetails.entity.Customer;
import com.example.democustomerdetails.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
class CustomerDetailsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerDetailsController customerDetailsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerDetailsController).build();
    }


    @Test
    void shouldGetCustomerServiceTest() throws Exception {
        mockMvc.perform(get("/customerDetails/customer-test"))
                .andExpect(status().isOk())
                .andExpect(content().string("customer-test"));
    }

    @Test
    void shouldGetCustomer() throws Exception {
        // given
        Long customerId = 1l;
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setFirstName("Shubham");
        customer.setLastName("Wattamwar");
        when(customerService.getCustomerById(customerId)).thenReturn(customer);

        // when-then
        mockMvc.perform(get("/customerDetails/customer?id=" + customerId)
                        .param("id", customerId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId))
                .andExpect(jsonPath("$.firstName").value("Shubham"))
                .andExpect(jsonPath("$.lastName").value("Wattamwar"));
    }

    @Test
    void shouldSaveCustomer() throws Exception {
        // given
        CustomerRequestObject request = new CustomerRequestObject();
        request.setFirstName("Shubham");

        String requestJson = "{ \"firstName\": \"Shubham\", \"lastName\": \"Wattamwar\" , \"dob\": \"27/09/1995\" }";

        String customerSavedSuccessfully = "Customer saved successfully";
        when(customerService.saveCustomer(any(CustomerRequestObject.class)))
                .thenReturn(customerSavedSuccessfully);

        //when-then
        mockMvc.perform(post("/customerDetails/customer")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string(customerSavedSuccessfully));
    }
}
