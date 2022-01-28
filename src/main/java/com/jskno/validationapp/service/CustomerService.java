package com.jskno.validationapp.service;

import com.jskno.validationapp.persistence.model.Customer;
import com.jskno.validationapp.web.dto.CustomerDTO;

import java.time.LocalDate;
import java.util.List;

public interface CustomerService {

    List<Customer> findAllCustomers();

    Customer findCustomerById(Long id);

    List<Customer> findCustomerByLastName(String name);

    Customer saveCustomer(Customer customer);

}
