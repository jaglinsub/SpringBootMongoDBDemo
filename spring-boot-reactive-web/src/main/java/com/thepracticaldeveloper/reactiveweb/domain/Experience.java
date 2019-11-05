package com.thepracticaldeveloper.reactiveweb.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

@Document(collection = "Experience")
public final class Experience {

    private String id;
    private String experienceName;
    private String roleName;
    private String activity;
    private String hoursspent;
    private String learnings;
    private String location;
    private String startDate;
    private String endDate;
    private String anythingElse;;

    private String userId;

    // Empty constructor is needed for Jackson to deserialize JSON
    public Experience() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExperienceName() {
        return experienceName;
    }

    public void setExperienceName(String experienceName) {
        this.experienceName = experienceName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getHoursspent() {
        return hoursspent;
    }

    public void setHoursspent(String hoursspent) {
        this.hoursspent = hoursspent;
    }

    public String getLearnings() {
        return learnings;
    }

    public void setLearnings(String learnings) {
        this.learnings = learnings;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAnythingElse() {
        return anythingElse;
    }

    public void setAnythingElse(String anythingElse) {
        this.anythingElse = anythingElse;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return getId().equals(that.getId()) &&
                getExperienceName().equals(that.getExperienceName()) &&
                getRoleName().equals(that.getRoleName()) &&
                getActivity().equals(that.getActivity()) &&
                getHoursspent().equals(that.getHoursspent()) &&
                getLearnings().equals(that.getLearnings()) &&
                getLocation().equals(that.getLocation()) &&
                getStartDate().equals(that.getStartDate()) &&
                getEndDate().equals(that.getEndDate()) &&
                getAnythingElse().equals(that.getAnythingElse()) &&
                Objects.equals(getUserId(), that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getExperienceName(), getRoleName(), getActivity(), getHoursspent(), getLearnings(), getLocation(), getStartDate(), getEndDate(), getAnythingElse(), getUserId());
    }

    @Override
    public String toString() {
        return "Experience{" +
                "id='" + id + '\'' +
                ", experienceName='" + experienceName + '\'' +
                ", roleName='" + roleName + '\'' +
                ", activity='" + activity + '\'' +
                ", hoursspent='" + hoursspent + '\'' +
                ", learnings='" + learnings + '\'' +
                ", location='" + location + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", anythingElse='" + anythingElse + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
