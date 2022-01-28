package com.jskno.validationapp.service;

import com.jskno.validationapp.persistence.model.Booking;
import com.jskno.validationapp.persistence.model.Customer;
import com.jskno.validationapp.web.validation.ConsistentDateParameters;
import com.jskno.validationapp.web.validation.ValidBooking;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    void createReservation(@NotNull @Future LocalDate begin, @Min(2) int duration, @NotNull Customer customer);

    @ConsistentDateParameters
    Booking createReservation(LocalDate begin, LocalDate end, Customer customer);

    List<Booking> getAllCustomers(boolean isValid);

    Booking createReservation(Booking booking);

    Booking getCustomCustomer(boolean isValid);

    List<Booking> getAllCustomCustomers(boolean isValid);

}
