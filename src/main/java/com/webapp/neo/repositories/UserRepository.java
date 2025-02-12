package com.webapp.neo.repositories;


import com.webapp.neo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends CrudRepository<UserEntity, Long> {
    //UserEntity findByUsername(String username);

}
