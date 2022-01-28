package com.jskno.validationapp.web.controller;

import com.github.dozermapper.core.Mapper;
import com.jskno.validationapp.persistence.model.Booking;
import com.jskno.validationapp.persistence.model.Customer;
import com.jskno.validationapp.service.BookingService;
import com.jskno.validationapp.web.dto.BookingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private BookingService bookingService;

    private Mapper mapper;

    @Autowired
    public BookingController(BookingService bookingService, Mapper mapper) {
        this.bookingService = bookingService;
        this.mapper = mapper;
    }

    @PostMapping("/singleparameterscontraints")
    public void createReservationWithSingleParametersConstraints(@RequestBody BookingDTO bookingDTO) {
        this.bookingService.createReservation(bookingDTO.getStartDate(), 3, this.mapper.map(bookingDTO.getCustomer(), Customer.class));
    }

    @PostMapping("/crossparametersconstraints")
    public BookingDTO createReservation(@RequestBody BookingDTO bookingDTO) {
        return this.mapper.map(
                this.bookingService.createReservation(this.mapper.map(bookingDTO, Booking.class)),
                BookingDTO.class);
    }

    @GetMapping("/returnconstraints")
    public List<BookingDTO> getAllBooking() {
        return this.bookingService.getAllCustomers(true).stream()
                .map(booking -> this.mapper.map(booking, BookingDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/customreturnconstraints")
    public BookingDTO getCustomBooking() {
        return this.mapper.map(this.bookingService.getCustomCustomer(true), BookingDTO.class);
    }

    @GetMapping("/customallreturnconstraints")
    public List<BookingDTO> getAllCustomBooking() {
        return this.bookingService.getAllCustomCustomers(true).stream()
                .map(booking -> this.mapper.map(booking, BookingDTO.class))
                .collect(Collectors.toList());
    }




}
