package com.viseo.companion.dto;

import java.util.ArrayList;
import java.util.List;

public class CommentDTO extends BaseDTO {
    String content;
    long datetime;
    long eventId;
    UserDTO writer;
    List<CommentDTO> childComments = new ArrayList<>();
    List<UserDTO> likers = new ArrayList<>();
    long nbLike;
    boolean publish;

    public CommentDTO() {
        super();
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public long getNbLike() {
        return nbLike;
    }

    public void setNbLike(long nbLike) {
        this.nbLike = nbLike;
    }

    public List<UserDTO> getLikers() {
        return likers;
    }

    public void setLikers(List<UserDTO> likers) {
        this.likers = likers;
    }

    public List<CommentDTO> getChildComments() {
        return childComments;
    }

    public void setChildComments(List<CommentDTO> childComments) {
        this.childComments = childComments;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public UserDTO getWriter() {
        return writer;
    }

    public void setWriter(UserDTO writer) {
        this.writer = writer;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }
}
