package com.viseo.companion.service;

import com.viseo.companion.converter.CommentConverter;
import com.viseo.companion.dao.CommentRepository;
import com.viseo.companion.dao.EventRepository;
import com.viseo.companion.dao.UzerRepository;
import com.viseo.companion.domain.Comment;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UzerRepository uzerRepository;

    public CommentDTO addComment(CommentDTO commentDTO) {
        CommentConverter converter = new CommentConverter();
        Comment comment = toComment(commentDTO);
        if (comment == null || !commentRepository.addComment(comment)) {
            return null;
        }
        return converter.getDTO(comment);
    }

    public boolean deleteComment(long commentId) {
        if (commentRepository.getComment(commentId) != null) {
            commentRepository.deleteComment(commentRepository.getComment(commentId));
            return true;
        }
        return false;
    }

    public CommentDTO updateComment(CommentDTO commentDTO) {
        try {
            CommentConverter converter = new CommentConverter();
            Comment comment = commentRepository.getComment(commentDTO.getId());
            if (comment == null) {
                return null;
            }
            converter.apply(commentDTO, comment);
            return converter.getDTO(commentRepository.updateComment(comment));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<CommentDTO> getCommentsByEvent(long eventId) {
        try {
            return toCommentDTOList(commentRepository.getCommentsByEvent(eventId));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    public CommentDTO getComment(long id) {
        CommentConverter converter = new CommentConverter();
        Comment comment = commentRepository.getComment(id);
        return converter.getDTO(comment);
    }

    public List<CommentDTO> getComments() {
        try {
            return toCommentDTOList(commentRepository.getComments());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    public boolean addChildComment(CommentDTO commentDTO, long parentId) {
        try {
            Comment childComment = toComment(commentDTO);
            if (childComment == null || !commentRepository.addComment(childComment)) {
                return false;
            }
            Comment parentComment = commentRepository.getComment(parentId);
            parentComment.addChild(childComment);
            commentRepository.updateComment(parentComment);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return true;
    }

    private Comment toComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        CommentConverter converter = new CommentConverter();
        Uzer uzer = uzerRepository.getUzer(commentDTO.getUserId());
        Event event = eventRepository.getEvent(commentDTO.getEventId());
        if (uzer == null || event == null) {
            return null;
        }
        comment.setUzer(uzer);
        comment.setEvent(event);
        converter.apply(commentDTO, comment);
        return comment;
    }

    private List<CommentDTO> toCommentDTOList(List<Comment> comments) {
        List<CommentDTO> result = new ArrayList<>();
        CommentConverter converter = new CommentConverter();
        for (Comment comment : comments) {
            result.add(converter.getDTO(comment));
        }
        return result;
    }
}
