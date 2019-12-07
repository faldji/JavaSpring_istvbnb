package com.istv.progcomp.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.istv.progcomp.data.ReservationRepository;
import com.istv.progcomp.form.SearchForm;
import com.istv.progcomp.model.LogementEntity;
import com.istv.progcomp.model.ReservationEntity;
import com.istv.progcomp.model.UserEntity;
import com.istv.progcomp.data.LogementRepository;
import com.istv.progcomp.data.UserRepository;
import com.istv.progcomp.service.LogementServ;
import com.istv.progcomp.service.UserRegisterServ;
import com.istv.progcomp.form.UserForm;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Controller pour les page index, création de compte et profile
 *
 */
@Controller
public class DefaultController{
    //Logger utiliter pour afficher les message d'erreur et warning dans la console
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultController.class);
    //MessageSource utiliser pour récuperer les textes selon la langue actuelle [en, fr]
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogementRepository logementRepository;
    @Autowired
    private LogementServ logementServ;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRegisterServ userRegisterServ;

    //GET HOME Request return index.hml
    @RequestMapping(value = {"/", "/home"})
    public String home(Model model, Principal principal) {
        Collection<LogementEntity> loadedValue = logementRepository.findLogementEntitiesByEnabledIsTrue();
        homeModelImg(model, loadedValue);
        model.addAttribute("searchForm",new SearchForm());
        model.addAttribute("isSearchResult",false);
        if (principal != null){
            UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName());
            Collection<ReservationEntity> resEntities;
            resEntities=reservationRepository.findReservationEntitiesByActiveIsTrueAndValidatedIsFalseAndBaileur(userEntity);
            if (resEntities != null)
                    model.addAttribute("activeRes",resEntities.size());
        }
        return "index";
    }

    //POST Search Request return index.hml
    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.POST)
    public ModelAndView search(@ModelAttribute("searchForm") @Valid SearchForm searchForm, Errors errors, Model model,
                               Principal principal){
        Collection<LogementEntity> loadedValue = logementServ.search(searchForm);
        if (errors.hasErrors())
            return new ModelAndView("index");
        homeModelImg(model, loadedValue);
        model.addAttribute("searchForm",searchForm);
        model.addAttribute("isSearchResult",true);
        if (principal != null){
            UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName());
            if (userEntity != null){
                Collection<ReservationEntity> resEntities;
                resEntities=reservationRepository.findReservationEntitiesByActiveIsTrueAndValidatedIsFalseAndBaileur(userEntity);
                if (resEntities != null)
                    model.addAttribute("activeRes",resEntities.size());
            }

        }

        return new ModelAndView("index","searchForm",searchForm);
    }

    //GET SIGNUP Request Formulaire de création d'un nouveau Spring Security user return signup.html
    @RequestMapping(value = "/signup")
    public String signup(Model model){
        String[] roles = {
                messageSource.getMessage("label.form.user.type.Admin",null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("label.form.user.type.User",null, LocaleContextHolder.getLocale())
                };
        model.addAttribute("roles",roles);
        model.addAttribute("user",new UserForm());
        return "signup";
    }

    //POST SIGNUP Request create new Spring Security user return signup.html
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserForm userForm,
                                            BindingResult result,
                                            Errors errors) {
        LOGGER.debug("Registering user account with information: {}", userForm);
        UserEntity registered = userRegisterServ.createUser(userForm,errors);
        if (registered == null || result.hasErrors()) {
            LOGGER.warn("Error list: {}", result.getAllErrors());
            ModelMap modelMap = new ModelMap("user",userForm);
            modelMap.addAttribute("roles",
                    new String[]{messageSource.getMessage("label.form.user.type.Admin",null, LocaleContextHolder.getLocale()),
                    messageSource.getMessage("label.form.user.type.User",null, LocaleContextHolder.getLocale())}
                    );
            return new ModelAndView("signup", modelMap);
        }
        return new ModelAndView("redirect:/login?regSuccess=true", "user", userForm);

    }

    //GET PROFILE Request return profile.html
    @RequestMapping(value = "/profile/{id}")
    public ModelAndView profile(Principal principal, Model model, @PathVariable String id, UserForm userForm){
        if (!principal.getName().equals(id))
            return new ModelAndView("redirect:/");
        UserEntity userEntity = userRepository.findUserEntityByUsername(id);
       if (userEntity == null)
           return new ModelAndView("redirect:/");
        userForm.setUsername(userEntity.getUsername());
        userForm.setEmail(userEntity.getEmail());
        userForm.setUsername(userEntity.getUsername());
        int[] roleVal;
        if (userEntity.getRoles().length == 2)
            roleVal = new int[]{0,1};
        else roleVal = userEntity.getRoles()[0].equals("ROLE_ADMIN")?new int[]{0}:new int[]{1};
        userForm.setRole(roleVal);
        model.addAttribute("user",userForm);
        model.addAttribute("roles", new String[]{messageSource.getMessage("label.form.user.type.Admin",null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("label.form.user.type.User",null, LocaleContextHolder.getLocale())});
        return new ModelAndView("profile","user",userForm);
    }

    /**
     * applique les attributs products = liste des logements et hType = types de logements
     * @param model Model
     * @param loadedValue Collection<LogementEntity>
     */
    private void homeModelImg(Model model, Collection<LogementEntity> loadedValue) {
        Collection<LogementEntity> logementEntities = new ArrayList<>();
        loadedValue.forEach(j->{
            if (j.getValidReservations().isEmpty())
                logementEntities.add(j);
        });

        logementEntities.forEach(l -> {if (l.getImg() == null) l.setImg("/img/defaultHouse.png");});
        model.addAttribute("products",logementEntities);
        String[] messageArray = {
                messageSource.getMessage("label.form.logement.Type.flat",null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("label.form.logement.Type.hut",null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("label.form.logement.Type.bungalow",null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("label.form.logement.Type.houseBarn",null, LocaleContextHolder.getLocale())
        };
        model.addAttribute("hType",messageArray);
    }
}
