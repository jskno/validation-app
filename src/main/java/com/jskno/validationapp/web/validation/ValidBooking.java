package com.jskno.validationapp.web.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ValidBookingValidator.class)
@Target({ ElementType.METHOD, ElementType.TYPE_USE, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidBooking {
    String message() default "End date must be after begin date "
            + "and both must be in the future, customer email must be informed";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
