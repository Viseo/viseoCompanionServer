package com.viseo.companion.dao;

import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Uzer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by HEL3666 on 24/04/2017.
 */
public interface UzerRepository extends CrudRepository<Uzer, Long> {

}
