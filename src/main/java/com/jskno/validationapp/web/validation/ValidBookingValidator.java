package com.jskno.validationapp.web.validation;

import com.jskno.validationapp.persistence.model.Booking;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ValidBookingValidator implements ConstraintValidator<ValidBooking, Booking> {

    @Override
    public boolean isValid(Booking booking, ConstraintValidatorContext constraintValidatorContext) {
        if (booking == null) {
            return true;
        }

        if (!(booking instanceof Booking)) {
            throw new IllegalArgumentException("Illegal method signature, "
                    + "expected parameter of type booking.");
        }

        if (booking.getStartDate() == null
                || booking.getEndDate() == null
                || booking.getCustomer() == null) {
            return false;
        }

        return (booking.getStartDate().isAfter(LocalDate.now())
                && booking.getStartDate().isBefore(booking.getEndDate())
                && booking.getCustomer().getEmail() != null);
    }
}
