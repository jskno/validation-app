package com.jskno.validationapp.web.controller;

import com.github.dozermapper.springboot.autoconfigure.DozerAutoConfiguration;
import com.jskno.validationapp.persistence.model.Customer;
import com.jskno.validationapp.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CustomerController.class)
@ImportAutoConfiguration(DozerAutoConfiguration.class)
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void whenPostRequestToCustomersAndValidCustomer_thenCorrectResponse() throws Exception {
        prepareServiceMock();

        String customer = "{\"firstName\": \"Jose\", \"lastName\": \"Cano\", \"email\" : \"jose@yahoo.es\", \"verifyEmail\" : \"jose@yahoo.es\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .content(customer)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                .contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void whenPostRequestToCustomersAndInValidCustomer_thenCorrectResponse() throws Exception {
        String customer = "{\"firstName\": \"\", \"email\" : \"bob@domain.com\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .content(customer)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiFieldErrors.length()").value(3))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.apiFieldErrors[0].message", Is.is("First Name is mandatory")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.apiFieldErrors[1].message", Is.is("Last Name is mandatory")))
                .andExpect(MockMvcResultMatchers.content()
                .contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    private void prepareServiceMock() {
        Customer newCustomer = new Customer();
        newCustomer.setFirstName("Jose");
        newCustomer.setLastName("Cano");
        newCustomer.setEmail("jose@yahoo.es");

        Mockito.when(customerService.saveCustomer(ArgumentMatchers.any(Customer.class)))
                .thenReturn(newCustomer);
    }
}