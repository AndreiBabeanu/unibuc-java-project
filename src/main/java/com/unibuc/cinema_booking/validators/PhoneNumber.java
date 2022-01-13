package com.unibuc.cinema_booking.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface PhoneNumber {

    String message() default "Phone Number not allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}