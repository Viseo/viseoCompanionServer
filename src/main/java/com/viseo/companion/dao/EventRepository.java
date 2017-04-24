package com.viseo.companion.dao;

/**
 * Created by IBO3693 on 21/04/2017.
 */


import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Uzer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface EventRepository extends CrudRepository<Event, Long> {
    /*@Query("select a from Event a left join fetch a.participants p left join fetch p.roles where a.id = :id")
    Event getEvent(long id);

    @Query("select a from Event a left join fetch a.participants p left join fetch p.roles where p.id = :id order by a.datetime")
    List<Event> getEventsByRegisteredUser(long userId);

    @Query("select distinct a from Event a left join fetch a.participants p left join fetch p.roles where a.datetime >= CURRENT_DATE order by a.datetime")
    List<Event> getEvents() ;
    Uzer getParticipant(long eventId, long userId);
    List<Uzer> getParticipants(long eventId);*/

}