package com.bw.weatherApi.validator.numberValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class EnsureNumberValidator implements ConstraintValidator<EnsureNumber,Long> {
    private EnsureNumber ensureNumber;

    @Override
    public void initialize(EnsureNumber constraintAnnotation) {
        this.ensureNumber = constraintAnnotation;
    }

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        if(aLong == null){
            return false;

        }
        // Initialize it.
        String regex = ensureNumber.decimal() ? "-?[0-9][0-9\\.\\,]*" : "-?[0-9]+";
        String data = String.valueOf(aLong);
        return data.matches(regex);
    }
}
