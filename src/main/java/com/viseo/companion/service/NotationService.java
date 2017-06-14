package com.viseo.companion.service;

import com.viseo.companion.dao.NotationRepository;
import com.viseo.companion.domain.Notation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotationService {
    @Autowired
    private NotationRepository notationRepository;

    public Notation addNotation(Notation notation) {
        return notationRepository.addNotation(notation);
    }

    public String getNotation(long eventId) {
        return notationRepository.getNotation(eventId);
    }

    public Notation updateNotation(Notation notation) {
        return notationRepository.updateNotation(notation);
    }
}
