package com.example.tasktrackerb7.validations;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValid implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password.length() > 6 && password.length() < 30) {
            return password.matches("^(?=.*[a-z])(?=\\\\S+$).{6,20}$");
        } else {
            return false;
        }
    }
}
