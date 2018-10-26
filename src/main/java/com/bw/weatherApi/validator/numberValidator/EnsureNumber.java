package com.bw.weatherApi.validator.numberValidator;




import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EnsureNumberValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnsureNumber {

    String message() default "{ Use only digit for role id}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean decimal() default false;
}
