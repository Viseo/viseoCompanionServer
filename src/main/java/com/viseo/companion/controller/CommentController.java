package com.viseo.companion.controller;

import com.viseo.companion.converter.CommentConverter;
import com.viseo.companion.domain.Comment;
import com.viseo.companion.dto.CommentDTO;
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
    public boolean addComment(@RequestBody CommentDTO commentDTO) {
        CommentConverter converter = new CommentConverter();
        Comment comment = new Comment();
        converter.apply(commentDTO, comment);
        return commentService.addComment(comment);
    }

    @RequestMapping(value = "${endpoint.deleteComment}", method = RequestMethod.DELETE)
    public Boolean removeComment(@PathVariable("commentId") long commentId) {
        return commentService.deleteComment(commentId);
    }

    @RequestMapping(value = "${endpoint.updateComment}", method = RequestMethod.PUT)
    public final Comment updateComment(@RequestBody Comment comment) {
        try {
            comment = commentService.updateComment(comment);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return comment;
    }

    @RequestMapping(value = "${endpoint.getCommentsByEvent}", method = RequestMethod.GET)
    public List<Comment> getCommentsByEvent(@PathVariable("eventId") long eventId) {
        return commentService.getCommentsByEvent(eventId);
    }

    @RequestMapping(value = "${endpoint.getComments}", method = RequestMethod.GET)
    public List<Comment> getComments() {
        return commentService.getComents();
    }

}
