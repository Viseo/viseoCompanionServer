package com.viseo.companion.dto;

import java.util.ArrayList;
import java.util.List;

public class CommentDTO extends BaseDTO {
    String content;
    long datetime;
    long userId;
    long eventId;
    List<CommentDTO> childComments = new ArrayList<>();

    public CommentDTO() {
        super();
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }
}
