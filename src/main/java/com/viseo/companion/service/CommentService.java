package com.viseo.companion.service;

import com.viseo.companion.converter.CommentConverter;
import com.viseo.companion.dao.CommentDao;
import com.viseo.companion.dao.EventDao;
import com.viseo.companion.dao.UserDao;
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
    private CommentDao commentDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private UserDao userDao;

    private CommentConverter converter = new CommentConverter();

    public CommentDTO addComment(CommentDTO commentDTO) {
        try {
            Comment comment = toComment(commentDTO);
            if (comment != null) {
                comment = commentDao.addComment(comment);
                return converter.getDTO(comment);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public CommentDTO getComment(long id) {
        try {
            Comment comment = commentDao.getComment(id);
            return converter.getDTO(comment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<CommentDTO> getComments() {
        try {
            return toCommentDTOList(commentDao.getComments());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<CommentDTO> getCommentsByEvent(long eventId, String filter) {
        try {
            if (filter == null || filter.equals("published")) {
                return toCommentDTOList(commentDao.getPublishedCommentsByEvent(eventId));
            } else if (filter.equals("all")) {
                return toCommentDTOList(commentDao.getAllCommentsByEvent(eventId));
            } else {
                return new ArrayList<>();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<CommentDTO> getCommentsByEventAfterDate(long eventId, String after) {
        try {
            return toCommentDTOList(commentDao.getCommentsByEventAfterDate(eventId, after));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public CommentDTO updateComment(CommentDTO commentDTO) {
        try {
            Comment comment = commentDao.getComment(commentDTO.getId());
            if (comment != null) {
                converter.apply(commentDTO, comment);
                return converter.getDTO(commentDao.updateComment(comment));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public void deleteComment(long commentId) {
        Comment childComment = commentDao.getComment(commentId);
        if (childComment != null) {
            Comment parentComment = commentDao.getParentFromChildId(commentId);
            if (parentComment != null) {
                parentComment.removeChild(childComment);
                commentDao.updateComment(parentComment);
            }
            commentDao.deleteComment(childComment);
        }
    }

    public CommentDTO addChildComment(CommentDTO commentDTO, long parentId) {
        try {
            Comment childComment = toComment(commentDTO);
            Comment parentComment = commentDao.getComment(parentId);
            if (childComment != null && parentComment != null) {
                commentDao.addComment(childComment);
                parentComment.addChild(childComment);
                commentDao.updateComment(parentComment);
                return converter.getDTO(childComment);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public boolean likeComment(long commentId, long userId) {
        try {
            Comment comment = commentDao.getComment(commentId);
            User user = userDao.getUser(userId);
            if (comment != null && user != null) {
                comment.addLiker(user);
                commentDao.updateComment(comment);
                return true;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }

    public boolean dislikeComment(long commentId, long userId) {
        try {
            Comment comment = commentDao.getComment(commentId);
            User user = userDao.getUser(userId);
            if (comment != null && user != null) {
                comment.removeliker(user);
                commentDao.updateComment(comment);
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
        User user = userDao.getUser(commentDTO.getWriter().getId());
        Event event = eventDao.getEvent(commentDTO.getEventId());
        if (user != null && event != null) {
            comment.setUser(user);
            comment.setEvent(event);
            converter.apply(commentDTO, comment);
            return comment;
        }
        return null;
    }

}
