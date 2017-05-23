package com.viseo.companion.converter;

import com.viseo.companion.domain.Comment;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.dto.CommentDTO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CommentConverter {
    static private long NULL = -1;
    static private long NEW = 0;

    public CommentDTO getDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setDatetime(comment.getDatetime().toInstant().toEpochMilli());
        dto.setVersion(comment.getVersion());
        if (comment.getUzer() == null) {
            dto.setUserId(NULL);
        } else {
            dto.setUserId(comment.getUzer().getId());
        }
        if (comment.getEvent() == null) {
            dto.setEventId(NULL);
        } else {
            dto.setEventId(comment.getEvent().getId());
        }
//        for(Comment child : comment.getChildren()) {
//            CommentDTO commentDTO = getDTO(child);
//            dto.getChildComments().add(commentDTO);
//        }
        List<CommentDTO> children = new ArrayList<>();
        if (comment.getChildren() != null) {
            for (Comment child : comment.getChildren()) {
                children.add(getDTO(child));
            }
        }
        dto.setChildComments(children);
        List<Long> likers = new ArrayList<>();
        if (comment.getLikers() != null) {
            for (Uzer liker : comment.getLikers()) {
                likers.add(liker.getId());
            }
        }
        dto.setLikerIds(likers);
        dto.setNbLike(likers.size());
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
}
