package com.viseo.companion.service;

import com.viseo.companion.converter.CommentConverter;
import com.viseo.companion.dao.CommentDAO;
import com.viseo.companion.dao.EventDAO;
import com.viseo.companion.dao.UserDAO;
import com.viseo.companion.domain.Comment;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.User;
import com.viseo.companion.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private UserDAO userDAO;

    private CommentConverter converter = new CommentConverter();

    public CommentDTO addComment(CommentDTO commentDTO) {
        try {
            Comment comment = toComment(commentDTO);
            if (comment != null) {
                comment = commentDAO.addComment(comment);
                return converter.getDTO(comment);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public CommentDTO getComment(long id) {
        try {
            Comment comment = commentDAO.getComment(id);
            return converter.getDTO(comment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<CommentDTO> getComments() {
        try {
            return toCommentDTOList(commentDAO.getComments());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<CommentDTO> getCommentsByEvent(long eventId, String filter) {
        try {
            if (filter == null || filter.equals("published")) {
                return toCommentDTOList(commentDAO.getPublishedCommentsByEvent(eventId));
            } else if (filter.equals("all")) {
                return toCommentDTOList(commentDAO.getAllCommentsByEvent(eventId));
            } else {
                return new ArrayList<>();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<CommentDTO> getCommentsByEventAfterDate(long eventId, String after) {
        try {
            return toCommentDTOList(commentDAO.getCommentsByEventAfterDate(eventId, after));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public CommentDTO updateComment(CommentDTO commentDTO) {
        try {
            Comment comment = commentDAO.getComment(commentDTO.getId());
            if (comment != null) {
                converter.apply(commentDTO, comment);
                return converter.getDTO(commentDAO.updateComment(comment));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public void deleteComment(long commentId) {
        Comment childComment = commentDAO.getComment(commentId);
        if (childComment != null) {
            Comment parentComment = commentDAO.getParentFromChildId(commentId);
            if (parentComment != null) {
                parentComment.removeChild(childComment);
                commentDAO.updateComment(parentComment);
            }
            commentDAO.deleteComment(childComment);
        }
    }

    public CommentDTO addChildComment(CommentDTO commentDTO, long parentId) {
        try {
            Comment childComment = toComment(commentDTO);
            Comment parentComment = commentDAO.getComment(parentId);
            if (childComment != null && parentComment != null) {
                commentDAO.addComment(childComment);
                parentComment.addChild(childComment);
                commentDAO.updateComment(parentComment);
                return converter.getDTO(childComment);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public boolean likeComment(long commentId, long userId) {
        try {
            Comment comment = commentDAO.getComment(commentId);
            User user = userDAO.getUser(userId);
            if (comment != null && user != null) {
                comment.addLiker(user);
                commentDAO.updateComment(comment);
                return true;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }

    public boolean dislikeComment(long commentId, long userId) {
        try {
            Comment comment = commentDAO.getComment(commentId);
            User user = userDAO.getUser(userId);
            if (comment != null && user != null) {
                comment.removeliker(user);
                commentDAO.updateComment(comment);
                return true;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }

    private List<CommentDTO> toCommentDTOList(List<Comment> comments) {
        List<CommentDTO> result = new ArrayList<>();
        for (Comment comment : comments) {
            result.add(converter.getDTO(comment));
        }
        return result;
    }

    private Comment toComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        User user = userDAO.getUser(commentDTO.getWriter().getId());
        Event event = eventDAO.getEvent(commentDTO.getEventId());
        if (user != null && event != null) {
            comment.setUser(user);
            comment.setEvent(event);
            converter.apply(commentDTO, comment);
            return comment;
        }
        return null;
    }

}
