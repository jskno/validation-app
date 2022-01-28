package com.jskno.validationapp.web.controller;

import com.github.dozermapper.core.Mapper;
import com.jskno.validationapp.persistence.model.Customer;
import com.jskno.validationapp.service.CustomerService;
import com.jskno.validationapp.web.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    private Mapper mapper;

    @Autowired
    public CustomerController(CustomerService customerService, Mapper mapper) {
        this.customerService = customerService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<CustomerDTO> findAll() {
        return customerService.findAllCustomers().stream()
                .map(customer -> mapper.map(customer, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CustomerDTO findCustomerById(@PathVariable Long id) {
        return mapper.map(customerService.findCustomerById(id), CustomerDTO.class);
    }

    @GetMapping("/search/{lastname}")
    public List<CustomerDTO> findCustomerByLastName(@PathVariable String lastname) {
        return customerService.findCustomerByLastName(lastname)
                .stream()
                .map(customer -> mapper.map(customer, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public CustomerDTO saveCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        return mapper.map(
                customerService.saveCustomer(mapper.map(customerDTO, Customer.class)),
                CustomerDTO.class);
    }
}
