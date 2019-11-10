package com.istv.progcomp.reposytory;

import com.istv.progcomp.entity.ReservationEntity;
import com.istv.progcomp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    ArrayList<ReservationEntity> findReservationEntitiesByBaileur(UserEntity baileur);
    ArrayList<ReservationEntity> findReservationEntitiesByLocataire(UserEntity locataire);
}
