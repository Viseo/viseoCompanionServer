package com.viseo.companion.service;

import com.viseo.companion.converter.NotationConverter;
import com.viseo.companion.dao.EventRepository;
import com.viseo.companion.dao.NotationRepository;
import com.viseo.companion.dao.UserRepository;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Notation;
import com.viseo.companion.domain.User;
import com.viseo.companion.dto.NotationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotationService {
    @Autowired
    private NotationRepository notationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;

    private NotationConverter converter = new NotationConverter();

    public NotationDTO addNotation(NotationDTO notationDto) {
        try {
            Notation notation = toNotation(notationDto);
            if (notation != null) {
                notation = notationRepository.addNotation(notation);
                return converter.getDTO(notation);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;

    }

    private Notation toNotation(NotationDTO notationDto) {
        Notation notation = new Notation();
        User user = userRepository.getUser(notationDto.getUserId());
        Event event = eventRepository.getEvent(notationDto.getEventId());
        if (user != null && event != null) {
            notation.setUser(user);
            notation.setEvent(event);
            converter.apply(notationDto, notation);
            return notation;
        }
        return null;
    }

    public String getNotationByEvent(long eventId) {
        return notationRepository.getNotationByEvent(eventId);
    }

    public Notation getNotation(long id) {
        return notationRepository.getNotation(id);
    }

    public NotationDTO updateNotation(NotationDTO notationDto) {
        try {
            Notation notation = notationRepository.getNotation(notationDto.getId());
            if (notation != null) {
                converter.apply(notationDto, notation);
                return converter.getDTO(notationRepository.updateNotation(notation));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }
}
