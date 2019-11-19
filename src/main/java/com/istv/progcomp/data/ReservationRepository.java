package com.istv.progcomp.data;

import com.istv.progcomp.model.LogementEntity;
import com.istv.progcomp.model.ReservationEntity;
import com.istv.progcomp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.ArrayList;
import java.util.Collection;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    ReservationEntity findReservationEntityById(long id);
    ArrayList<ReservationEntity> findReservationEntitiesByBaileur(UserEntity baileur);
    ArrayList<ReservationEntity> findReservationEntitiesByBaileurAndActiveIsTrueAndValidatedIsFalse(UserEntity baileur);
    ArrayList<ReservationEntity> findReservationEntitiesByLocataire(UserEntity locataire);
    ArrayList<ReservationEntity> findReservationEntitiesByActiveIsTrueAndValidatedIsFalseAndBaileur(UserEntity baileur);
    Collection<ReservationEntity> findReservationEntitiesByLogementAndActiveIsTrueAndIdIsNotAndValidatedIsFalse(LogementEntity logement, long id);
}
