package com.viseo.companion.dto;

import java.io.Serializable;

public class LiveActionDTO implements Serializable {

    int type;
    Object payload;

    public LiveActionDTO(int type, Object payload) {
        this.type = type;
        this.payload = payload;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
