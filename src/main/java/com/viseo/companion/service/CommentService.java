package com.viseo.companion.service;

import com.viseo.companion.dao.CommentRepository;
import com.viseo.companion.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public boolean addComment(Comment comment) {
        try {
            return commentRepository.addComment(comment);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean deleteComment(long commentId) {
        if (commentRepository.getComment(commentId) != null) {
            commentRepository.deleteComment(commentRepository.getComment(commentId));
            return true;
        }
        return false;
    }

    public Comment updateComment(Comment comment) {
        return commentRepository.updateComment(comment);
    }

    public List<Comment> getCommentsByEvent(long eventId) {
        return commentRepository.getCommentsByEvent(eventId);
    }

    public Comment getComment(long id) {
        Comment comment = commentRepository.getComment(id);
        return comment;
    }

    public List<Comment> getComments() {
        List<Comment> comments = null;
        try {
            comments = commentRepository.getComments();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return comments;
    }
}
