package com.viseo.companion.converter;

import com.viseo.companion.dto.ChatMessageDTO;
import com.viseo.companion.dto.CommentDTO;
import com.viseo.companion.dto.UserDTO;

public class ChatMessageConverter {

    static private long NULL = -1;

    public ChatMessageDTO getDTO(CommentDTO comment) {
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setContent(comment.getContent());
        dto.setDateTime(comment.getDatetime());
        if (comment.getWriter() == null) {
            dto.setWriterId(NULL);
        } else {
            dto.setWriterId(comment.getWriter().getId());
        }
        if (comment.getEventId() == NULL) {
            dto.setEventId(NULL);
        } else {
            dto.setEventId(comment.getEventId());
        }
        return dto;
    }

    public void apply(ChatMessageDTO dto, CommentDTO comment) {
        comment.setContent(dto.getContent());
        comment.setDatetime(dto.getDateTime());
        UserDTO writer = new UserDTO();
        writer.setId(dto.getWriterId());
        comment.setWriter(writer);
        comment.setEventId(dto.getEventId());
    }
}
