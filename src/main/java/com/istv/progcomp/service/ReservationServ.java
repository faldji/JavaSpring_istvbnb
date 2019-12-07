package com.istv.progcomp.service;

import com.istv.progcomp.data.LogementRepository;
import com.istv.progcomp.data.ReservationRepository;
import com.istv.progcomp.model.LogementEntity;
import com.istv.progcomp.model.ReservationEntity;
import com.istv.progcomp.model.UserEntity;
import com.istv.progcomp.service.imp.ReservationServImpl;
import com.istv.progcomp.form.ReservationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Collection;
import java.util.Date;

@Service
public class ReservationServ implements ReservationServImpl {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private LogementRepository logementRepository;

    @Override
    public ReservationForm edit(long edit) {
        ReservationForm reservationForm = new ReservationForm();
        ReservationEntity reservationEntity = reservationRepository.findReservationEntityById(edit);
        if (reservationEntity == null)
            return null;
        reservationForm.setDateDebut(reservationEntity.getDateDebut());
        reservationForm.setDateFin(reservationEntity.getDateFin());
        reservationForm.setNbrOccupant(reservationEntity.getNbrOccupant());
        reservationForm.setPrice(reservationEntity.getPrice());
        return  reservationForm;

    }

    @Override
    public ReservationForm handleAdd(long edit) {
        ReservationForm reservationForm = new ReservationForm();
        LogementEntity logementEntity = logementRepository.findLogementEntityById(edit);
        if (logementEntity == null)
            return null;
        reservationForm.setPrice(logementEntity.getPrice());
        return reservationForm;
    }

    @Override
    public ReservationEntity addNewReservation(long id, ReservationForm reservationForm, UserEntity locataire, Errors errors) {
        if (errors.hasErrors())
            return null;
        LogementEntity logementEntity = logementRepository.findLogementEntityById(id);
        if (logementEntity ==null)
            return null;
        ReservationEntity reservationEntity = new ReservationEntity();
        if (logementEntity.getNbrLoc() < reservationForm.getNbrOccupant()){
            errors.rejectValue("nbrOccupant","message.resNbrGuestError");
            return null;
        }
        reservationEntity.setActive(true);
        reservationEntity.setBaileur(logementEntity.getBailleur());
        reservationEntity.setCreatedAt(new Date());
        reservationEntity.setDateDebut(reservationForm.getDateDebut());
        reservationEntity.setDateFin(reservationForm.getDateFin());
        reservationEntity.setLocataire(locataire);
        reservationEntity.setLogement(logementEntity);
        reservationEntity.setNbrOccupant(reservationForm.getNbrOccupant());
        reservationEntity.setPrice(logementEntity.getPrice());
        reservationEntity.setValidated(false);
        return reservationRepository.save(reservationEntity);
    }

    @Override
    public ReservationEntity editReservation(long id, ReservationForm reservationForm, UserEntity userEntity, Errors errors) {
        if (errors.hasErrors())
            return null;
        ReservationEntity reservationEntity = reservationRepository.findReservationEntityById(id);
        if (reservationEntity ==null)
            return null;
        LogementEntity logementEntity = reservationEntity.getLogement();
        if (logementEntity ==null)
            return null;
        if (!userEntity.equals(reservationEntity.getLocataire()))
            return null;
        if (logementEntity.getNbrLoc() < reservationForm.getNbrOccupant()){
            errors.rejectValue("nbrOccupant","message.resNbrGuestError");
            return null;
        }
        reservationEntity.setDateDebut(reservationForm.getDateDebut());
        reservationEntity.setDateFin(reservationForm.getDateFin());
        reservationEntity.setNbrOccupant(reservationForm.getNbrOccupant());
        reservationEntity.setValidated(false);
        return reservationRepository.save(reservationEntity);
    }

    @Override
    public ReservationEntity validReservation(long id, UserEntity userEntity) {
        ReservationEntity reservationEntity = reservationRepository.findReservationEntityById(id);
        if (reservationEntity ==null)
            return null;

        LogementEntity logementEntity = reservationEntity.getLogement();
        if (logementEntity == null)
            return null;
        if (!userEntity.equals(logementEntity.getBailleur()))
            return null;
        reservationEntity.setValidated(true);
        reservationRepository.save(reservationEntity);
        Collection<ReservationEntity> reservationEntities = reservationRepository
                .findReservationEntitiesByLogementAndActiveIsTrueAndIdIsNotAndValidatedIsFalse(logementEntity,reservationEntity.getId());
       if (reservationEntities != null){
           reservationEntities.forEach(e->e.setActive(false));
           reservationRepository.saveAll(reservationEntities);
       }
        return reservationEntity;
    }

}
