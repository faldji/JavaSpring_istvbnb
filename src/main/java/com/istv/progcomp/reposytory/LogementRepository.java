package com.istv.progcomp.reposytory;

import com.istv.progcomp.entity.LogementEntity;
import com.istv.progcomp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface LogementRepository extends CrudRepository<LogementEntity, Long> {
    ArrayList<LogementEntity> findLogementEntitiesByBailleur(UserEntity bailleur);
}
