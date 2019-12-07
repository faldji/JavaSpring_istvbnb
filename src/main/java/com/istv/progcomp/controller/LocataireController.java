package com.istv.progcomp.controller;

import com.istv.progcomp.data.ReservationRepository;
import com.istv.progcomp.data.UserRepository;
import com.istv.progcomp.model.ReservationEntity;
import com.istv.progcomp.model.UserEntity;
import com.istv.progcomp.service.ReservationServ;
import com.istv.progcomp.form.ReservationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

@Controller
public class LocataireController {

    private UserRepository userRepository;
    private ReservationRepository reservationRepository;
    private ReservationServ reservationServ;
    @Autowired
    LocataireController(UserRepository userRepository, ReservationServ reservationServ,
                        ReservationRepository reservationRepository){
        this.userRepository = userRepository;
        this.reservationServ = reservationServ;
        this.reservationRepository =reservationRepository;

    }
    @RequestMapping(value = "/reservation")
    public String listReservation(Principal principal, Model model,
                                  @RequestParam(required = false, defaultValue = "0") Long id,
                                  @RequestParam(required = false) String add,
                               @RequestParam(required = false, defaultValue = "0") Long edit,
                                  @RequestParam(required = false, defaultValue = "0") long cancel,
                               @RequestParam(required = false, defaultValue = "0") long delete){
        if (principal == null)
            return "redirect:home";
        UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName());
        if (userEntity == null)
            return "redirect:home";
        model.addAttribute("user",userEntity);
        model.addAttribute("typeModal","add");
        Collection<ReservationEntity> resEntities;
        resEntities=reservationRepository.findReservationEntitiesByLocataire(userEntity);
        model.addAttribute("reservations",resEntities);
        model.addAttribute("reservationForm",new ReservationForm());
        if (resEntities != null) {
            model.addAttribute("reservations", resEntities);
        }
        if (add != null && id != 0) {
            ReservationForm reservationForm = reservationServ.handleAdd(id);
            if (reservationForm != null){
                model.addAttribute("reservationForm",reservationForm);
                model.addAttribute("price",reservationForm.getPrice());
            }
            model.addAttribute("showResModal",true);
            model.addAttribute("idAdd",id);
            return "reservation";
        }
        if (edit != 0) {
            ReservationForm reservationForm = reservationServ.edit(edit);
            if (reservationForm != null) {
                model.addAttribute("reservationForm", reservationForm);
                model.addAttribute("price",reservationForm.getPrice());
            }
            model.addAttribute("typeModal","edit");
            model.addAttribute("showResModal",true);
            model.addAttribute("idEdit",edit);
            return "reservation";
        }

        if (delete!=0){
            ReservationEntity re = reservationRepository.findReservationEntityById(delete);
            if (re != null)
                reservationRepository.delete(re);
            return "redirect:/reservation";
        }
        if (cancel!=0){
            ReservationEntity ca = reservationRepository.findReservationEntityById(cancel);
            if (ca != null && ca.getLocataire().equals(userEntity)) {
                ca.setActive(false);
                reservationRepository.save(ca);
            }
            return "redirect:/reservation";
        }


        return "reservation";
    }
    @RequestMapping(value = "/reservation/{path}/{id}", method = RequestMethod.POST)
    public String addLogement(@ModelAttribute("reservationForm") @Valid ReservationForm reservationForm,
                                    BindingResult result, Errors errors, Model model, Principal principal,
                                    @PathVariable String path, @PathVariable long id){
        if (principal == null)
            return  "redirect:/home";
        UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName());
        if (userEntity == null)
            return "redirect:/home";
        Collection<ReservationEntity> reservations = reservationRepository.findReservationEntitiesByLocataire(userEntity);
        model.addAttribute("user",userEntity);
        model.addAttribute("reservationForm",reservationForm);
        model.addAttribute("reservations",reservations);
        model.addAttribute("typeModal",path);
        model.addAttribute("idEdit",id);
        model.addAttribute("idAdd",id);
        switch (path) {
            case "add": {
                ReservationEntity res = reservationServ.addNewReservation(id, reservationForm, userEntity, errors);
                if (res == null || result.hasErrors()) {
                    model.addAttribute("showResModal", true);
                    return "reservation";
                }
                return "redirect:/reservation";
            }
            case "edit": {
                ReservationEntity res = reservationServ.editReservation(id, reservationForm, userEntity, errors);
                if (res == null || result.hasErrors()) {
                    model.addAttribute("showResModal", true);
                    return "reservation";
                }
                return "redirect:/reservation";
            }
            case "validate":
                ReservationEntity validReservation = reservationServ.validReservation(id, userEntity);
                if (validReservation == null) {
                    return "logement";
                }
                return "redirect:/logement";
        }
        return "reservation";
    }
}
