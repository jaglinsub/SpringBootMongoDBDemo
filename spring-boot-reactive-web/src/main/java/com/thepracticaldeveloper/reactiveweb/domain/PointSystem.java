package com.thepracticaldeveloper.reactiveweb.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.Objects;

@Document(collection = "PointSystem")
public final class PointSystem {

    private String id;
    private String activityName;
    private String activityPoint;
    private Integer displayOrder;

    // Empty constructor is needed for Jackson to deserialize JSON
    public PointSystem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityPoint() {
        return activityPoint;
    }

    public void setActivityPoint(String activityPoint) {
        this.activityPoint = activityPoint;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
