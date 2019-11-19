package com.istv.progcomp.data;

import com.istv.progcomp.model.LogementEntity;
import com.istv.progcomp.model.ReservationEntity;
import com.istv.progcomp.model.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.ArrayList;
import java.util.Collection;

public interface LogementRepository extends CrudRepository<LogementEntity, Long> {

    ArrayList<LogementEntity> findLogementEntitiesByBailleur(UserEntity bailleur);
    LogementEntity findLogementEntityById(long id);
    Collection<LogementEntity> findLogementEntitiesByEnabledIsTrue();

}
