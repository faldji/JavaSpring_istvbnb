package com.istv.progcomp.data;

import com.istv.progcomp.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    List<UserEntity> findUserEntitiesByUsername(String username);
    UserEntity findUserEntityById(long id);
    UserEntity findUserEntityByUsername(String username);
    UserEntity findUserEntityByEmail(String email);
}
