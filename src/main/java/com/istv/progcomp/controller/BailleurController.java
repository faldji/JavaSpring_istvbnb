package com.istv.progcomp.controller;

import com.istv.progcomp.model.LogementEntity;
import com.istv.progcomp.model.ReservationEntity;
import com.istv.progcomp.model.UserEntity;
import com.istv.progcomp.data.LogementRepository;
import com.istv.progcomp.data.ReservationRepository;
import com.istv.progcomp.data.UserRepository;
import com.istv.progcomp.service.LogementServ;
import com.istv.progcomp.form.LogementForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;

@Controller
public class BailleurController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    LogementRepository logementRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    private LogementServ logementServ;
    @Autowired
    private MessageSource messageSource;
    @RequestMapping(value = "/logement")
    public String listLogement(Principal principal, Model model,
                               @RequestParam(required = false ) String add,
                               @RequestParam(required = false, defaultValue = "0") long disable,
                               @RequestParam(required = false, defaultValue = "0") Long edit,
                               @RequestParam(required = false, defaultValue = "0") long enable,
                               @RequestParam(required = false, defaultValue = "0") long delete){
        if (principal == null)
            return "redirect:/home";
        UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName());
        if (userEntity == null)
            return "redirect:/home";
        Collection<LogementEntity> logements = logementRepository.findLogementEntitiesByBailleur(userEntity);
        String typeForm = "add";
        LogementForm logementForm = new LogementForm();
        Collection<ReservationEntity> reservations = reservationRepository.findReservationEntitiesByBaileurAndActiveIsTrueAndValidatedIsFalse(userEntity);
        String[] messageArray = {
                messageSource.getMessage("label.form.logement.Type.flat",null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("label.form.logement.Type.hut",null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("label.form.logement.Type.bungalow",null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("label.form.logement.Type.houseBarn",null, LocaleContextHolder.getLocale())
        };

        model.addAttribute("hType",messageArray );
        model.addAttribute("user",userEntity);
        model.addAttribute("user",userEntity);
        model.addAttribute("logements",logements);
        model.addAttribute("reservations",reservations);
        model.addAttribute("typeForm",typeForm);
        model.addAttribute("logementForm",logementForm);
        if (add != null) {
            typeForm = "add";
            model.addAttribute("typeForm",typeForm);
            model.addAttribute("showModal",true);
            return "logement";
        }
        if (delete!=0){
            model.addAttribute("typeForm",typeForm);
            LogementEntity le = logementRepository.findLogementEntityById(delete);
            if (le != null)
                logementRepository.delete(le);
            return "redirect:/logement";
        }
        if (disable!=0){
            model.addAttribute("typeForm",typeForm);
            LogementEntity le = logementRepository.findLogementEntityById(disable);
            if (le != null){
                le.setEnabled(false);
                logementRepository.save(le);
            }
            return "logement";
        }
        if (enable!=0){
            model.addAttribute("typeForm",typeForm);
            LogementEntity le = logementRepository.findLogementEntityById(enable);
            if (le != null){
                le.setEnabled(true);
                logementRepository.save(le);
            }
            return "logement";
        }
        if (edit != 0) {
            typeForm = "edit";
            logementForm= logementServ.handleEdit(edit);
            if (logementForm != null)
                model.addAttribute("logementForm",logementForm);
            model.addAttribute("typeForm",typeForm);
            model.addAttribute("showModal",true);
            model.addAttribute("idEdit",edit);
            return "logement";
        }

        return "logement";
    }

    @RequestMapping(value = "/logement/{add}", method = RequestMethod.POST)
    public ModelAndView addLogement(@ModelAttribute("logementForm") @Valid LogementForm logementForm,
                                       BindingResult result, Errors errors, Model model, Principal principal,
                                       @PathVariable String add,
                                       @RequestParam(value = "img", required = false) MultipartFile file) throws IOException {
        if (principal == null)
            return new ModelAndView("redirect:/");
        UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName());
        if (userEntity == null)
            return new ModelAndView("redirect:/");
        Collection<LogementEntity> logements = logementRepository.findLogementEntitiesByBailleur(userEntity);
        Collection<ReservationEntity> reservations = reservationRepository.findReservationEntitiesByBaileur(userEntity);
        String[] messageArray = {
                messageSource.getMessage("label.form.logement.Type.flat",null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("label.form.logement.Type.hut",null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("label.form.logement.Type.bungalow",null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("label.form.logement.Type.houseBarn",null, LocaleContextHolder.getLocale())
        };
        model.addAttribute("hType",messageArray );
        model.addAttribute("logementForm",logementForm);
        model.addAttribute("user",userEntity);
        model.addAttribute("logements",logements);
        model.addAttribute("reservations",reservations);
        model.addAttribute("typeForm",add);
        LogementEntity lHouse = logementServ.addNewLogement(logementForm, userEntity, errors, file);
            if (lHouse == null || result.hasErrors()) {
                model.addAttribute("showModal",true);
                return new ModelAndView("/logement");
            }
        return new ModelAndView("redirect:/logement","logementForm",logementForm);
    }

    @RequestMapping(value = "/logement/{path}/{id}", method = RequestMethod.POST)
    public ModelAndView handleLogement(@ModelAttribute("logementForm") @Valid LogementForm logementForm,
                                    BindingResult result, Errors errors, Model model, Principal principal,
                                       @PathVariable String path,@PathVariable long id,
                                    @RequestParam(value = "img", required = false) MultipartFile file) throws IOException {
        if (principal == null)
            return new ModelAndView("redirect:/home");
        UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName());
        if (userEntity == null)
            return new ModelAndView("redirect:/home");
        Collection<LogementEntity> logements = logementRepository.findLogementEntitiesByBailleur(userEntity);
        Collection<ReservationEntity> reservations = reservationRepository.findReservationEntitiesByBaileur(userEntity);
        String[] messageArray = {
                messageSource.getMessage("label.form.logement.Type.flat",null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("label.form.logement.Type.hut",null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("label.form.logement.Type.bungalow",null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("label.form.logement.Type.houseBarn",null, LocaleContextHolder.getLocale())
        };
        model.addAttribute("hType",messageArray );
        model.addAttribute("user",userEntity);
        model.addAttribute("logementForm",logementForm);
        model.addAttribute("user",userEntity);
        model.addAttribute("logements",logements);
        model.addAttribute("reservations",reservations);
        model.addAttribute("typeForm",path);
        /** edit de logement*/
        if (path.equals("edit")){
            LogementEntity lHouse = logementServ.editLogement(logementForm, id, errors);
            if (lHouse == null || result.hasErrors()) {
                model.addAttribute("showModal",true);
                return new ModelAndView("/logement");
            }
        }
        return new ModelAndView("redirect:/logement","logementForm",logementForm);
    }
}
