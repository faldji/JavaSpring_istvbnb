package com.istv.progcomp.service;


import com.istv.progcomp.model.UserEntity;
import com.istv.progcomp.data.UserRepository;
import com.istv.progcomp.service.imp.UserRegisterImpl;
import com.istv.progcomp.form.exception.UniqueUserException;
import com.istv.progcomp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

@Service
public class UserRegisterServ implements UserRegisterImpl{
    @Autowired
    private UserRepository repository;
    private boolean emailExists(String email) {
        return repository.findUserEntityByEmail(email) != null;
    }
    private boolean usernameExists(String username) {
        return repository.findUserEntityByUsername(username) != null;
    }
    @Transactional
    @Override
    public UserEntity createUser(UserForm userForm, Errors errors) throws UniqueUserException {
        if (usernameExists(userForm.getUsername()))
            errors.rejectValue("username","message.regUserError");
        if (emailExists(userForm.getEmail()))
            errors.rejectValue("email","message.regEmailError");
        if (errors.hasErrors()) {
            if (errors.hasGlobalErrors())
                errors.rejectValue("matchingPassword", "message.regPasswordError");
            return null;
        }
        UserEntity user = new UserEntity();
        user.setUsername(userForm.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword()));
        user.setEmail(userForm.getEmail());
        String[] roleVal;
        if (userForm.getRole().length == 2)
            roleVal = new String[]{"ROLE_ADMIN","ROLE_USER"};
        else
            roleVal = userForm.getRole()[0] == 0 ?  new String[]{"ROLE_ADMIN"}: new String[]{"ROLE_USER"};
        user.setRoles(roleVal);
        return repository.save(user);
    }
}
