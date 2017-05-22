package com.viseo.companion.controller;

import com.viseo.companion.domain.Comment;
import com.viseo.companion.service.CommentService;
import com.viseo.companion.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class CommentController {

    @Autowired
    private EventService eventService;

    @Autowired
    CommentService commentService;

    @RequestMapping(value = "${endpoint.addComment}", method = POST)
    public boolean addComment(@RequestBody Comment comment) {
        if (comment.getUzer() != null || comment.getEvent() != null) {
            return commentService.addComment(comment);
        }
        return false;
    }

    @RequestMapping(value = "${endpoint.deleteComment}", method = RequestMethod.DELETE)
    public Boolean removeComment(@PathVariable("commentId") long commentId) {
        return commentService.deleteComment(commentId);
    }

    @RequestMapping(value = "${endpoint.updateComment}", method = RequestMethod.PUT)
    public final Comment updateComment(@RequestBody Comment comment) {
        Comment updatedComment = null;
        try {
            updatedComment = commentService.updateComment(comment);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return updatedComment;
    }

    @RequestMapping(value = "${endpoint.getCommentsByEvent}", method = RequestMethod.GET)
    public List<Comment> getCommentsByEvent(@PathVariable("eventId") long eventId) {
        return commentService.getCommentsByEvent(eventId);
    }

    @RequestMapping(value = "${endpoint.getComments}", method = RequestMethod.GET)
    public List<Comment> getComments() {
        return commentService.getComments();
    }

}
