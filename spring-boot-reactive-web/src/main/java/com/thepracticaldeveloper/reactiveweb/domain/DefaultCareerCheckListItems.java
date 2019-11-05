package com.thepracticaldeveloper.reactiveweb.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "DefaultCareerCheckListItems")
public class DefaultCareerCheckListItems {

    private String id;
    private String checkListItemTitle;
    private Integer points;
    private Integer displayOrder;

    // Empty constructor is needed for Jackson to deserialize JSON
    public DefaultCareerCheckListItems() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckListItemTitle() {
        return checkListItemTitle;
    }

    public void setCheckListItemTitle(String checkListItemTitle) {
        this.checkListItemTitle = checkListItemTitle;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Override
    public String toString() {
        return "DefaultCareerCheckListItems{" +
                "id='" + id + '\'' +
                ", checkListItemTitle='" + checkListItemTitle + '\'' +
                ", points=" + points +
                ", displayOrder=" + displayOrder +
                '}';
    }
}
