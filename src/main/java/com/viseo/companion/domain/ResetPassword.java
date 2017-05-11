package com.viseo.companion.domain;

/**
 * Created by LMA3606 on 11/05/2017.
 */
public class ResetPassword {

    private long uzerId;
    private String tokenGuid;
    private String password;

    public long getUzerId() {
        return uzerId;
    }

    public void setUzerId(long uzerId) {
        this.uzerId = uzerId;
    }

    public String getTokenGuid() {
        return tokenGuid;
    }

    public void setTokenGuid(String tokenGuid) {
        this.tokenGuid = tokenGuid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
