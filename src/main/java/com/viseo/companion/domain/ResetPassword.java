package com.viseo.companion.domain;

public class ResetPassword {

    private long uzerId;
    private String tokenGuid;
    private String password;

    public ResetPassword() {
    }

    public ResetPassword(long uzerId, String tokenGuid, String password) {
        this.uzerId = uzerId;
        this.tokenGuid = tokenGuid;
        this.password = password;
    }

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
