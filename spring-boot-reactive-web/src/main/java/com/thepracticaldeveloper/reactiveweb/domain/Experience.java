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
    private String startMonth;
    private String startYear;
    private String endMonth;
    private String endYear;
    private String activityType;
    private String hoursspent;

    private String responsibility1;
    private String responsibility2;
    private String responsibility3;

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

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getHoursspent() {
        return hoursspent;
    }

    public void setHoursspent(String hoursspent) {
        this.hoursspent = hoursspent;
    }

    public String getResponsibility1() {
        return responsibility1;
    }

    public void setResponsibility1(String responsibility1) {
        this.responsibility1 = responsibility1;
    }

    public String getResponsibility2() {
        return responsibility2;
    }

    public void setResponsibility2(String responsibility2) {
        this.responsibility2 = responsibility2;
    }

    public String getResponsibility3() {
        return responsibility3;
    }

    public void setResponsibility3(String responsibility3) {
        this.responsibility3 = responsibility3;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
