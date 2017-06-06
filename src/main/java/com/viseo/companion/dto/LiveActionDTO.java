package com.viseo.companion.dto;

import java.io.Serializable;

public class LiveActionDTO implements Serializable {

    int type;
    String payload;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
