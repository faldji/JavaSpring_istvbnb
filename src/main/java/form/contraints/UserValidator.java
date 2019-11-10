package form.contraints;

import form.UserForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UserForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username","message.username","Username is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"email","message.email","Email is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"email","message.password","Password is required");
    }
}
