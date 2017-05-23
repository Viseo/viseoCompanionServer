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

    CommentConverter converter = new CommentConverter();

    public CommentDTO addComment(CommentDTO commentDTO) {
        try {
            Comment comment = toComment(commentDTO);
            if (comment == null) {
                return null;
            }
            comment = commentRepository.addComment(comment);
            return converter.getDTO(comment);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean deleteComment(long commentId) {
        Comment childComment = commentRepository.getComment(commentId);
        if (childComment == null) {
            return false;
        }
        Comment parentComment = commentRepository.getParentFromChildId(commentId);
        if (parentComment != null) {
            parentComment.removeChild(childComment);
            commentRepository.updateComment(parentComment);
        }
        return commentRepository.deleteComment(childComment);
    }

    public CommentDTO updateComment(CommentDTO commentDTO) {
        try {
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
            if (childComment == null || commentRepository.addComment(childComment) == null) {
                return false;
            }
            Comment parentComment = commentRepository.getComment(parentId);
            if (parentComment == null) {
                commentRepository.deleteComment(childComment);
                return false;
            }
            parentComment.addChild(childComment);
            commentRepository.updateComment(parentComment);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return true;
    }

    private Comment toComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        Uzer uzer = uzerRepository.getUzer(commentDTO.getUserId());
        Event event = eventRepository.getEvent(commentDTO.getEventId());
        if (uzer != null || event != null) {
            comment.setUzer(uzer);
            comment.setEvent(event);
            converter.apply(commentDTO, comment);
            return comment;
        }
        return null;
    }

    private List<CommentDTO> toCommentDTOList(List<Comment> comments) {
        List<CommentDTO> result = new ArrayList<>();
        for (Comment comment : comments) {
            result.add(converter.getDTO(comment));
        }
        return result;
    }

    public boolean likeComment(long commentId, long uzerId) {
        try {
            Comment comment = commentRepository.getComment(commentId);
            Uzer uzer = uzerRepository.getUzer(uzerId);
            if (comment != null && uzer != null) {
                comment.addLiker(uzer);
                commentRepository.updateComment(comment);
                return true;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }

    public boolean dislikeComment(long commentId, long uzerId) {
        try {
            Comment comment = commentRepository.getComment(commentId);
            Uzer uzer = uzerRepository.getUzer(uzerId);
            if (comment != null && uzer != null) {
                comment.removeliker(uzer);
                commentRepository.updateComment(comment);
                return true;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }
}
