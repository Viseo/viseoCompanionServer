package com.viseo.companion.dto;
public class NotationDTO  extends  BaseDTO{
    private long userId;
    private long eventId;
    private Integer notation;
    private String avis;
    public NotationDTO() {
        super();
    }

    public long getUserId() {
        return userId;
    }

    public long getEventId() {
        return eventId;
    }

    public Integer getNotation() {
        return notation;
    }

    public String getAvis() {
        return avis;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public void setNotation(Integer notation) {
        this.notation = notation;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }


}
