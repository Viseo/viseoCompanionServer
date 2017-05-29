package com.viseo.companion.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Calendar;

@Entity
public class PasswordResetToken extends BaseEntity {

    private static final int EXPIRATION = 60 * 24;

    private String guid;

    @OneToOne
    private User user;

    private Calendar expiryDate;

    public User getUser() {
        return user;
    }

    public PasswordResetToken(String guid, User user) {
        this.guid = guid;
        this.user = user;
        setExpiryDate(2);
    }

    public PasswordResetToken() {
    }

    public String getGuid() {
        return guid;
    }

    public boolean isUnexpired() {
        return expiryDate.after(Calendar.getInstance());
    }

    private void setExpiryDate(int howManyHours) {
        expiryDate = Calendar.getInstance();
        expiryDate.add(Calendar.HOUR_OF_DAY, howManyHours);
    }
}
