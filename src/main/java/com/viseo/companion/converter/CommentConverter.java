package com.viseo.companion.converter;

import com.viseo.companion.domain.Comment;
import com.viseo.companion.domain.User;
import com.viseo.companion.dto.ChatMessageDTO;
import com.viseo.companion.dto.CommentDTO;
import com.viseo.companion.dto.UserDTO;

import java.util.Calendar;

public class CommentConverter {
    static private long NULL = -1;
    static private long NEW = 0;

    private UserConverter userConverter = new UserConverter();

    public CommentDTO getDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setDatetime(comment.getDatetime().toInstant().toEpochMilli());
        dto.setVersion(comment.getVersion());
        if (comment.getUser() == null) {
            dto.setWriter(null);
        } else {
            dto.setWriter(userConverter.getDTO(comment.getUser()));
        }
        if (comment.getEvent() == null) {
            dto.setEventId(NULL);
        } else {
            dto.setEventId(comment.getEvent().getId());
        }
        for (Comment child : comment.getChildren()) {
            CommentDTO commentChildDTO = getDTO(child);
            dto.getChildComments().add(commentChildDTO);
        }
        for (User liker : comment.getLikers()) {
            UserDTO likerDTO = userConverter.getDTO(liker);
            dto.getLikers().add(likerDTO);
        }
        dto.setNbLike(dto.getLikers().size());
        return dto;
    }

    public void apply(CommentDTO dto, Comment comment) {
        if (dto.getId() != NEW && comment.getVersion() != dto.getVersion()) {
            throw new RuntimeException("Entity " + comment + " was updated since DTO was built.");
        }
        comment.setContent(dto.getContent());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dto.getDatetime());
        comment.setDatetime(calendar);
    }

    public CommentDTO getDTO(ChatMessageDTO chatMessage) {
        CommentDTO dto = new CommentDTO();
        dto.setContent(chatMessage.getContent());
        dto.setDatetime(chatMessage.getDateTime());
        UserDTO writer = new UserDTO();
        writer.setId(chatMessage.getWriterId());
        dto.setWriter(writer);
        dto.setEventId(chatMessage.getEventId());
        return dto;
    }

}
