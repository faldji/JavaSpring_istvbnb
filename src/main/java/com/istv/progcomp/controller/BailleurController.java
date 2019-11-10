package com.istv.progcomp.controller;

import com.istv.progcomp.entity.LogementEntity;
import com.istv.progcomp.entity.ReservationEntity;
import com.istv.progcomp.entity.UserEntity;
import com.istv.progcomp.reposytory.LogementRepository;
import com.istv.progcomp.reposytory.ReservationRepository;
import com.istv.progcomp.reposytory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @RequestMapping(value = "/logement/{id}")
    public String listLogement(@PathVariable String id, Principal principal, Model model){
        if (!principal.getName().equals(id))
            return "redirect:/home";
        UserEntity userEntity = userRepository.findUserEntityByUsername(id);
        if (userEntity == null)
        return "redirect:/home";
        Collection<LogementEntity> logements = logementRepository.findLogementEntitiesByBailleur(userEntity);
        Collection<ReservationEntity> reservations = reservationRepository.findReservationEntitiesByBaileur(userEntity);
        model.addAttribute("user",userEntity);
        model.addAttribute("logements",logements);
        model.addAttribute("reservations",reservations);
        return "logement";
    }


    @RequestMapping(value = "/logement/{id}/add")
    public String addLogement(@PathVariable String id, Principal principal, Model model){
        return "logement";
    }
    @RequestMapping(value = "/logement/{userId}/edit/{logementId}")
    public String editLogement(@PathVariable String id, Principal principal, Model model){
        return "logement";
    }
    @RequestMapping(value = "/logement/{userId}/delete/{logementId}")
    public String deleteLogement(@PathVariable String userId,@PathVariable String logementId, Principal principal, Model model){
        return "logement";
    }
}
