package com.viseo.companion.converter;

import com.viseo.companion.dto.ChatMessageDTO;
import com.viseo.companion.dto.CommentDTO;
import com.viseo.companion.dto.UserDTO;

public class ChatMessageConverter {

    static private long NULL = -1;

    public ChatMessageDTO getDTO(CommentDTO comment) {
        ChatMessageDTO dto = new ChatMessageDTO();
        if (comment.getEventId() == NULL) {
            dto.setEventId(NULL);
            return dto;
        } else {
            dto.setEventId(comment.getEventId());
        }
        dto.setDatetime(comment.getDatetime());
        dto.setContent(comment.getContent());
        if (comment.getWriter() == null) {
            dto.setWriterId(NULL);
        } else {
            dto.setWriterId(comment.getWriter().getId());
            dto.setWriter(comment.getWriter());
        }
        return dto;
    }

    public void apply(ChatMessageDTO dto, CommentDTO comment) {
        comment.setContent(dto.getContent());
        comment.setDatetime(dto.getDatetime());
        UserDTO writer = new UserDTO();
        writer.setId(dto.getWriterId());
        comment.setWriter(writer);
        comment.setEventId(dto.getEventId());
    }
}
