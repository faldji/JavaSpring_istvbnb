package com.istv.progcomp.service.imp;

import com.istv.progcomp.model.ReservationEntity;
import com.istv.progcomp.model.UserEntity;
import com.istv.progcomp.form.ReservationForm;
import org.springframework.validation.Errors;

public interface ReservationServImpl {
    ReservationForm edit(long edit);
    ReservationForm handleAdd(long edit);

    ReservationEntity addNewReservation(long id, ReservationForm reservationForm, UserEntity userEntity, Errors errors);

    ReservationEntity editReservation(long id, ReservationForm reservationForm, UserEntity userEntity, Errors errors);

    ReservationEntity validReservation(long id, UserEntity userEntity);
}
