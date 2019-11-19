package com.istv.progcomp.controller;


import com.istv.progcomp.data.ReservationRepository;
import com.istv.progcomp.model.LogementEntity;
import com.istv.progcomp.model.ReservationEntity;
import com.istv.progcomp.model.UserEntity;
import com.istv.progcomp.data.LogementRepository;
import com.istv.progcomp.data.UserRepository;
import com.istv.progcomp.service.UserRegisterServ;
import form.UserForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

@Controller
public class DefaultController{
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultController.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogementRepository logementRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRegisterServ userRegisterServ;
    @Autowired
    private MessageSource messageSource;
    @RequestMapping(value = {"/", "/home"})
    public String home(Model model, Principal principal){
        Collection<LogementEntity> loadedValue = logementRepository.findLogementEntitiesByEnabledIsTrue();
        Collection<LogementEntity> logementEntities = new ArrayList<>();
        loadedValue.forEach(j->{
                if (j.getValidReservations().isEmpty())
                    logementEntities.add(j);
        });
        loadedValue =null;
        logementEntities.forEach(l -> {if (l.getImg() == null) l.setImg("/img/defaultHouse.png");});
        model.addAttribute("products",logementEntities);
        String[] messageArray = {
                messageSource.getMessage("label.form.logement.Type.flat",null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("label.form.logement.Type.hut",null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("label.form.logement.Type.bungalow",null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("label.form.logement.Type.houseBarn",null, LocaleContextHolder.getLocale())
        };
        model.addAttribute("hType",messageArray);
        if (principal != null){
            UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName());
            if (userEntity != null){
                Collection<ReservationEntity> resEntities;
                resEntities=reservationRepository.findReservationEntitiesByActiveIsTrueAndValidatedIsFalseAndBaileur(userEntity);
                if (resEntities != null)
                    model.addAttribute("activeRes",resEntities.size());
            }

        }

        return "index";
    }
    @RequestMapping(value = "/signup")
    public String signup(Model model){
        String[] roles = {"ROLE_ADMIN","ROLE_USER"};
        model.addAttribute("roles",roles);
        model.addAttribute("user",new UserForm());
        return "signup";
    }
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserForm userForm,
                                            BindingResult result,
                                            WebRequest request,
                                            Errors errors) {
        LOGGER.debug("Registering user account with information: {}", userForm);
        UserEntity registered = userRegisterServ.createUser(userForm,errors);
        if (registered == null || result.hasErrors()) {
            LOGGER.warn("Error list: {}", result.getAllErrors());
            ModelMap modelMap = new ModelMap("user",userForm);
            modelMap.addAttribute("roles", new String[]{"ROLE_ADMIN", "ROLE_USER"});
            return new ModelAndView("signup", modelMap);
        }
        return new ModelAndView("redirect:/login", "user", userForm);

    }
    @RequestMapping(value = "/profile/{id}")
    public ModelAndView profile(Principal principal, Model model, @PathVariable String id, UserForm userForm){
        if (!principal.getName().equals(id))
            return new ModelAndView("redirect:/home");
        UserEntity userEntity = userRepository.findUserEntityByUsername(id);
       if (userEntity == null)
           return new ModelAndView("redirect:/home");
        userForm.setUsername(userEntity.getUsername());
        userForm.setEmail(userEntity.getEmail());
        userForm.setUsername(userEntity.getUsername());
        userForm.setRole(userEntity.getRoles());
        model.addAttribute("user",userForm);
        model.addAttribute("roles", new String[]{"ROLE_ADMIN", "ROLE_USER"});
        return new ModelAndView("profile","user",userForm);
    }
}
