package com.projetointegrado.MeuBolso.globalConstraints.validEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
    private Set<String> validValues;

    @Override
    public void initialize(ValidEnum annotation) {
        validValues = Arrays.stream(annotation.value().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && validValues.contains(value);
    }
}

