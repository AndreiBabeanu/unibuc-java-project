package com.unibuc.cinema_booking.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OnlyNumbersValidator.class)
public @interface OnlyNumbers {

    String message() default "Only numbers required.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
