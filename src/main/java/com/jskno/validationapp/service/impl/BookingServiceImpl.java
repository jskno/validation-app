package com.jskno.validationapp.service.impl;

import com.jskno.validationapp.persistence.model.Booking;
import com.jskno.validationapp.persistence.model.Customer;
import com.jskno.validationapp.service.BookingService;
import com.jskno.validationapp.web.validation.ConsistentDateParameters;
import com.jskno.validationapp.web.validation.ValidBooking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Validated
public class BookingServiceImpl implements BookingService {

    private static final Logger LOG = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Override
    public void createReservation(@NotNull @Future LocalDate begin, @Min(2) int duration, @NotNull Customer customer) {
        LOG.info("Validation passed !!");
    }

    @Override
    public Booking createReservation(Booking booking) {
        return this.createReservation(booking.getStartDate(), booking.getEndDate(), booking.getCustomer());
    }

    @Override
    @ConsistentDateParameters
    public Booking createReservation(LocalDate begin, LocalDate end, Customer customer) {
        LOG.info("Validation Passed !!");
        return null;
    }

    @Override
    @NotNull(message = "Return value must not be null")
    @Size(min = 1, message = "Return array must contain at least one element")
    public List<@NotNull Booking> getAllCustomers(boolean isValid) {
        return isValid ? Arrays.asList(new Booking()) : new ArrayList<>();
    }

    @Override
    @ValidBooking
    public Booking getCustomCustomer(boolean isValid) {
        return isValid ? createValidBooking() : createInvalidBooking();
    }

    @Override
    public List<@ValidBooking Booking> getAllCustomCustomers(boolean isValid) {
        return isValid ? createValidBookings() : createInvalidBookings();
    }

    private List<Booking> createValidBookings() {
        return Arrays.asList(createValidBooking(), createValidBooking(), createValidBooking());
    }

    private List<Booking> createInvalidBookings() {
        return Arrays.asList(createValidBooking(), createInvalidBooking(), createInvalidBooking());
    }

    private Booking createValidBooking() {
        Booking validBooking = new Booking();
        validBooking.setStartDate(LocalDate.now().plusDays(2));
        validBooking.setEndDate(LocalDate.now().plusDays(5));
        Customer customer = new Customer();
        customer.setEmail("jcano@gmmail.com");
        validBooking.setCustomer(customer);
        return validBooking;
    }

    private Booking createInvalidBooking() {
        Booking invalidBooking = new Booking();
        invalidBooking.setStartDate(LocalDate.now().plusDays(2));
        invalidBooking.setEndDate(LocalDate.now().plusDays(5));
        Customer customer = new Customer();
//        customer.setEmail("jcano@gmmail.com");
        invalidBooking.setCustomer(customer);
        return invalidBooking;
    }


}
