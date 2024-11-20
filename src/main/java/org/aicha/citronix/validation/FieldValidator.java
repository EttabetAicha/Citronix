package org.aicha.citronix.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.aicha.citronix.domain.Field;

public class FieldValidator implements ConstraintValidator<ValidField, Field> {

    @Override
    public boolean isValid(Field field, ConstraintValidatorContext context) {
        boolean isValid = true;

        if (field.getArea() < 0.1) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Field area must be at least 0.1 hectare")
                    .addConstraintViolation();
            isValid = false;
        }
        if (field.getArea() > field.getFarm().getTotalArea() * 0.5) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Field area must not exceed 50% of the total farm area")
                    .addConstraintViolation();
            isValid = false;
        }

        if (field.getTrees().size() > field.getArea() * 100) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Tree density must not exceed 100 trees per hectare")
                    .addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}