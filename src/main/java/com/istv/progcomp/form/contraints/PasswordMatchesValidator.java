package com.istv.progcomp.form.contraints;

import com.istv.progcomp.form.UserForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserForm user = (UserForm) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }
}
