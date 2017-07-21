package com.viseo.companion.dto;

public class ChatMessageDTO {

    String content;
    long datetime;
    long eventId;
    long writerId;
    UserDTO writer;

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

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getWriterId() {
        return writerId;
    }

    public void setWriterId(long writerId) {
        this.writerId = writerId;
    }

    public void setWriter(UserDTO writer) {
        this.writer = writer;
    }

    public UserDTO getWriter() {
        return writer;
    }

}
