package com.jskno.validationapp;

import com.jskno.validationapp.persistence.model.Customer;
import com.jskno.validationapp.service.BookingService;
import com.jskno.validationapp.service.CustomerService;
import com.jskno.validationapp.web.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@SpringBootApplication
public class ValidationAppApplication implements CommandLineRunner {

//    @Autowired
//    private BookingService bookingService;

    public static void main(String[] args) {
        SpringApplication.run(ValidationAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        bookingService.createReservation(LocalDate.now().plusDays(1), LocalDate.now().plusDays(5), new CustomerDTO());
//        bookingService.createReservation(LocalDate.now().plusDays(5), LocalDate.now().plusDays(1), new CustomerDTO());

    }

}
