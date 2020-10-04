package com.example.ClientsApi.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RightTypeValidation  implements ConstraintValidator<RightType, String> {
    @Override
    public void initialize(RightType constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return (s.equals("Mobile")||s.equals("Home")||s.equals("Office"))? true:false;
    }
}
