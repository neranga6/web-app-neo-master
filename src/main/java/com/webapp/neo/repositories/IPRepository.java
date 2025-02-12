package com.webapp.neo.repositories;

import com.webapp.neo.model.ContactForm;
import com.webapp.neo.model.IPDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface IPRepository extends JpaRepository<IPDetails, Long> {

}
