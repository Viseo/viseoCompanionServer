package com.viseo.companion.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;


@Entity
public class Activity extends BaseEntity {

    @ManyToOne
    private Action action;

    @ManyToOne
    private User user;

    @OneToMany
    private List<Mean> means;

    private String title;
    private String description;

    private String etat; // init by default with the value Etat.WAITING.toString();

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date dateStart;


    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date dateRelease;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date dateValidation;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date dateEnd;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date dateCreation; // init by default with the current date

    private String address;
    private int vizzWon = 0;
    private Boolean practice;
    private String readingTime;
    private String recurrence;
    private String publicationType;



    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public String getPublicationType() {
        return publicationType;
    }

    public void setPublicationType(String publicationType) {
        this.publicationType = publicationType;
    }

    public Activity() {
    }

    public Activity(Action action, User user, List<Mean> means, String title, String description, String etat,
                    Date dateStart, Date dateRelease, Date dateValidation, Date dateEnd, Date dateCreation,
                    String address, int vizzWon, Boolean practice, String readingTime,
                    String recurrence, String publicationType) {
        this.action = action;
        this.user = user;
        this.means = means;
        this.title = title;
        this.description = description;
        this.etat = etat;
        this.dateStart = dateStart;
        this.dateRelease = dateRelease;
        this.dateValidation = dateValidation;
        this.dateEnd = dateEnd;
        this.dateCreation = dateCreation;
        this.address = address;
        this.vizzWon = vizzWon;
        this.practice = practice;
        this.readingTime = readingTime;
        this.recurrence = recurrence;
        this.publicationType = publicationType;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateRelease() {
        return dateRelease;
    }

    public void setDateRelease(Date dateRelease) {
        this.dateRelease = dateRelease;
    }

    public Date getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(Date dateValidation) {
        this.dateValidation = dateValidation;
    }

    public List<Mean> getMeans() {
        return means;
    }

    public void setMeans(List<Mean> means) {
        this.means = means;
    }

    public int getVizzWon() {
        return vizzWon;
    }

    public void setVizzWon(int vizzWon) {
        this.vizzWon = vizzWon;
    }

    public Boolean getPractice() {
        return practice;
    }

    public void setPractice(Boolean practice) {
        this.practice = practice;
    }

    public String getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(String readingTime) {
        this.readingTime = readingTime;
    }



    public int numberOfVizzs() {
        int numberOfVizzs = 0;
        for (Mean mean : this.means) {
            numberOfVizzs += mean.getVizzsPerMean();
        }
        return numberOfVizzs;
    }
}
