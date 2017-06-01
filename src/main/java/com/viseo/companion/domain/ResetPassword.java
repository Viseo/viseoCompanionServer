package com.viseo.companion.domain;

public class ResetPassword {

    private long userId;
    private String tokenGuid;
    private String password;

    public ResetPassword() {
    }

    public ResetPassword(long userId, String tokenGuid, String password) {
        this.userId = userId;
        this.tokenGuid = tokenGuid;
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
