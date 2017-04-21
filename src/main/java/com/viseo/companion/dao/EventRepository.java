package com.viseo.companion.dao;

/**
 * Created by IBO3693 on 21/04/2017.
 */


import com.viseo.companion.domain.Event;
import org.springframework.data.repository.CrudRepository;



public interface EventRepository extends CrudRepository<Event, Long> {


}