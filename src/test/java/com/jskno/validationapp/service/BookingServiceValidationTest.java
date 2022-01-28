package com.jskno.validationapp.service;

import com.jskno.validationapp.persistence.model.Booking;
import com.jskno.validationapp.persistence.model.Customer;
import com.jskno.validationapp.web.dto.CustomerDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BookingServiceValidationTest {

    @Autowired
    private BookingService bookingService;

    // TEST FOR SINGLE PARAMETERS VALIDATION

    @Test
    public void testingSingleParametersConstraints_validationPass() {
        Customer customer = new Customer();
        bookingService.createReservation(LocalDate.now().plusDays(2), 2, customer);

    }

    @Test
    public void testingSingleParameterConstraints_validationFail() {
        ConstraintViolationException exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            Customer customer = new Customer();
            bookingService.createReservation(LocalDate.now().minusDays(2), 1, null);
        });

        List<String> expectedMessages = Arrays.asList(
                "must be a future date",
                "must be greater than or equal to 2",
                "must not be null"
        );
        List<String> actualMessages = exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        Assertions.assertEquals(exception.getConstraintViolations().size(), 3);
        Assertions.assertEquals(expectedMessages, actualMessages);
    }

    // TEST FOR CROSS PARAMETERS VALIDATION (always CUSTOM)

    @Test
    public void testingCrossParameterConstraints_validationPass() {
        Customer customer = new Customer();
        bookingService.createReservation(LocalDate.now().plusDays(2), LocalDate.now().plusDays(5), customer);

    }

    @Test
    public void testingCrossParameterConstraints_validationFail() {
        Exception exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            Customer customer = new Customer();
            bookingService.createReservation(LocalDate.now().plusDays(5), LocalDate.now().plusDays(2), customer);
        });

        String expectedMessage = "End date must be after begin date and both must be in the future";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    // TEST FOR RETURN VALUE VALIDATION

    @Test
    public void testingReturnValueConstraints_validationPass() {
        bookingService.getAllCustomers(true);

    }

    @Test
    public void testingReturnValueConstraints_validationFail() {
        ConstraintViolationException exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            bookingService.getAllCustomers(false);
        });

        String expectedMessages = "Return array must contain at least one element";
        String actualMessage = exception.getConstraintViolations().iterator().next().getMessage();

        Assertions.assertEquals(exception.getConstraintViolations().size(), 1);
        Assertions.assertEquals(expectedMessages, actualMessage);
    }

    // TEST FOR CUSTOM RETURN VALUE VALIDATION

    @Test
    public void testingCustomReturnValueConstraints_validationPass() {
        bookingService.getCustomCustomer(true);
    }

    @Test
    public void testingCustomReturnValueConstraints_validationFail() {
        ConstraintViolationException exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            bookingService.getCustomCustomer(false);
        });

        String expectedMessages = "End date must be after begin date and both must be in the future, customer email must be informed";
        String actualMessage = exception.getConstraintViolations().iterator().next().getMessage();

        Assertions.assertEquals(exception.getConstraintViolations().size(), 1);
        Assertions.assertEquals(expectedMessages, actualMessage);
    }

    @Test
    public void testingCustomListReturnValueConstraints_validationPass() {
        bookingService.getAllCustomCustomers(true);

    }

    @Test
    public void testingCustomListReturnValueConstraints_validationFail() {
        ConstraintViolationException exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            bookingService.getAllCustomCustomers(false);
        });

        String expectedMessages = "End date must be after begin date and both must be in the future, customer email must be informed";
        String actualMessage = exception.getConstraintViolations().iterator().next().getMessage();

        Assertions.assertEquals(exception.getConstraintViolations().size(), 2);
        Assertions.assertEquals(expectedMessages, actualMessage);
    }

    @Test
    public void testingCustomConstructorReturnValueConstraints_validationFail() {
        ConstraintViolationException exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            new Booking(1L, LocalDate.now().plusDays(5), LocalDate.now().plusDays(2), new Customer());
        });

        String expectedMessages = "End date must be after begin date and both must be in the future, customer email must be informed";
        String actualMessage = exception.getConstraintViolations().iterator().next().getMessage();

        Assertions.assertEquals(exception.getConstraintViolations().size(), 1);
        Assertions.assertEquals(expectedMessages, actualMessage);
    }


}
