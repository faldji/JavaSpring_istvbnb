package com.istv.progcomp.service.imp;

import com.istv.progcomp.entity.UserEntity;
import form.exception.UniqueUserException;
import form.UserForm;
import org.springframework.validation.Errors;

public interface UserRegisterImpl {
        UserEntity createUser(UserForm userForm, Errors errors) throws UniqueUserException;

}
