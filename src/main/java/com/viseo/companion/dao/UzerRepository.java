package com.viseo.companion.dao;

import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Uzer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.List;

/**
 * Created by HEL3666 on 24/04/2017.
 */

@RepositoryRestResource(collectionResourceRel = "uzer", path = "users")
public interface UzerRepository extends CrudRepository<Uzer, Long> {


    @Query("select c from Uzer c left join fetch c.roles where c.email like :email")
    List<Uzer> getUserByEmail(@Param("email") String email);



}
