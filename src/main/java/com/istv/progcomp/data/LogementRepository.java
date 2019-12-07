package com.istv.progcomp.data;

import com.istv.progcomp.model.LogementEntity;
import com.istv.progcomp.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public interface LogementRepository extends CrudRepository<LogementEntity, Long> {

    ArrayList<LogementEntity> findLogementEntitiesByBailleur(UserEntity bailleur);
    LogementEntity findLogementEntityById(long id);
    Collection<LogementEntity> findLogementEntitiesByEnabledIsTrue();
    Collection<LogementEntity> findLogementEntitiesByAddressContainingIgnoreCaseAndHouseYearGreaterThanEqualAndPriceGreaterThanEqualAndEnabledIsTrue(String address, Date houseYear, int price);
    Collection<LogementEntity> findLogementEntitiesByAddressContainingIgnoreCaseAndPriceIsGreaterThanEqualAndHouseYearIsGreaterThanEqualAndEnabledIsTrue(String address, int price, Date houseYear);
}
