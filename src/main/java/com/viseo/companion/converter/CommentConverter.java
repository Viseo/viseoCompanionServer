package com.viseo.companion.converter;

import com.viseo.companion.dao.EventRepository;
import com.viseo.companion.domain.Comment;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.dto.CommentDTO;
import com.viseo.companion.service.UzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

public class CommentConverter {
    static private long NULL = -1;
    static private long NEW = 0;

    @Autowired
    private UzerService uzerService;

    @Autowired
    EventRepository eventDao;

    public CommentDTO getDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setDatetime(comment.getDatetime().toInstant().getEpochSecond());
        dto.setVersion(comment.getVersion());
        if (comment.getUzer() == null) {
            dto.setUserId(NULL);
        } else {
            dto.setUserId(comment.getUzer().getId());
        }

        if (comment.getEvenement() == null) {
            dto.setEventId(NULL);
        } else {
            dto.setEventId(comment.getEvenement().getId());
        }
        return dto;
    }

    public void apply(CommentDTO dto, Comment comment){
        if(dto.getId() != NEW && comment.getVersion() != dto.getVersion()){
            throw new RuntimeException("Entity " + comment + " was updated since DTO was built.");
        }
        comment.setContent(dto.getContent());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dto.getDatetime());
        comment.setDatetime(calendar);
        if(comment.getUzer() == null) {
            Uzer uzer = uzerService.getUser(dto.getUserId());
            comment.setUzer(uzer);
        }
        if(comment.getEvenement() == null) {
            throw new RuntimeException("Entity " + comment + " is detached from its event.");
        } else {
            Event event = eventDao.getEvent(dto.getEventId());
            comment.setEvenement(event);
        }
    }
}
