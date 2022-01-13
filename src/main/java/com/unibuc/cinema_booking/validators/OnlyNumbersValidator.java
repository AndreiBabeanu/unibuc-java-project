package com.unibuc.cinema_booking.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OnlyNumbersValidator implements ConstraintValidator<OnlyNumbers, String> {

@Override
public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || value.chars().allMatch(Character::isDigit);
}
}
