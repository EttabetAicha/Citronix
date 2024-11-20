package org.aicha.citronix.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.aicha.citronix.domain.Farm;

public class FarmValidator implements ConstraintValidator<ValidFarm, Farm> {

    @Override
    public boolean isValid(Farm farm, ConstraintValidatorContext context) {
        if (farm.getFields().size() <= 10) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("A farm cannot contain more than 10 fields")
                .addConstraintViolation();
        return false;
    }
}