package com.jskno.validationapp.persistence.repository;

import com.jskno.validationapp.persistence.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String name);
}
