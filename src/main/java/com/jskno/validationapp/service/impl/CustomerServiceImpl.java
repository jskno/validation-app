package com.jskno.validationapp.service.impl;

import com.jskno.validationapp.persistence.model.Customer;
import com.jskno.validationapp.persistence.repository.CustomerRepository;
import com.jskno.validationapp.service.BookingService;
import com.jskno.validationapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    private BookingService bookingService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, BookingService bookingService) {
        this.customerRepository = customerRepository;
        this.bookingService = bookingService;
    }

    @Override
    public List<Customer> findAllCustomers() {
//        bookingService.createReservation(LocalDate.now().plusDays(1), LocalDate.now().plusDays(5), new CustomerDTO());
//        bookingService.createReservation(LocalDate.now().plusDays(5), LocalDate.now().plusDays(1), new CustomerDTO());
        bookingService.getAllCustomers(false);
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    public List<Customer> findCustomerByLastName(String lastName) {
        return customerRepository.findByLastName(lastName);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
