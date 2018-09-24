package com.bw.weatherApi.validator.EmailValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CustomEmailValidator.class )
@Target({ElementType.METHOD,  ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailValidatorConstraits {

    String message() default "Not a valid email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
