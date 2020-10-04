package com.example.ClientsApi.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RightTypeValidation.class )
public @interface RightType {
    String message() default "Type value is not valid";
    Class<?>[] groups() default {};
    public abstract Class<? extends Payload>[] payload() default {};
}
