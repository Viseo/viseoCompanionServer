package com.viseo.companion.controller;

import com.viseo.companion.dto.CommentDTO;
import com.viseo.companion.service.CommentService;
import com.viseo.companion.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @RequestMapping(value = "${endpoint.addComment}", method = POST)
    public CommentDTO addComment(@RequestBody CommentDTO comment) {
        return commentService.addComment(comment);
    }

    @RequestMapping(value = "${endpoint.addChildComment}", method = POST)
    public boolean addChildComment(@PathVariable("parentId") long parentId, @RequestBody CommentDTO comment) {
        return commentService.addChildComment(comment, parentId);
    }

    @RequestMapping(value = "${endpoint.deleteComment}", method = DELETE)
    public Boolean removeComment(@PathVariable("commentId") long commentId) {
        return commentService.deleteComment(commentId);
    }

    @RequestMapping(value = "${endpoint.updateComment}", method = PUT)
    public CommentDTO updateComment(@RequestBody CommentDTO commentDTO) {
        return commentService.updateComment(commentDTO);
    }

    @RequestMapping(value = "${endpoint.getComment}", method = GET)
    public CommentDTO getComment(@PathVariable("commentId") long commentId) {
        return commentService.getComment(commentId);
    }

    @RequestMapping(value = "${endpoint.getCommentsByEvent}", method = GET)
    public List<CommentDTO> getCommentsByEvent(@PathVariable("eventId") long eventId) {
        return commentService.getCommentsByEvent(eventId);
    }

    @RequestMapping(value = "${endpoint.getComments}", method = GET)
    public List<CommentDTO> getComments() {
        return commentService.getComments();
    }

    @RequestMapping(value = "${endpoint.likeComment}", method = POST)
    public boolean likeComment(@PathVariable("commentId") long commentId, @PathVariable("uzerId") long uzerId) {
        return commentService.likeComment(commentId, uzerId);
    }

    @RequestMapping(value = "${endpoint.dislikeComment}", method = DELETE)
    public boolean dislikeComment(@PathVariable("commentId") long commentId, @PathVariable("uzerId") long uzerId) {
        return commentService.dislikeComment(commentId, uzerId);
    }
}
