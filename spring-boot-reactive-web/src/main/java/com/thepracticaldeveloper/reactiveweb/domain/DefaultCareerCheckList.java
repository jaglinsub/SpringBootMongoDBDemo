package com.thepracticaldeveloper.reactiveweb.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Document(collection = "DefaultCareerCheckList")
public class DefaultCareerCheckList {

    private String id;
    private String checkListTitle;
    private Integer displayOrder;
    @DBRef
    private DefaultCareerCheckListItems defaultCareerCheckListItems[];

    // Empty constructor is needed for Jackson to deserialize JSON
    public DefaultCareerCheckList() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckListTitle() {
        return checkListTitle;
    }

    public void setCheckListTitle(String checkListTitle) {
        this.checkListTitle = checkListTitle;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public DefaultCareerCheckListItems[] getDefaultCareerCheckListItems() {
        return defaultCareerCheckListItems;
    }

    public void setDefaultCareerCheckListItems(DefaultCareerCheckListItems[] defaultCareerCheckListItems) {
        this.defaultCareerCheckListItems = defaultCareerCheckListItems;
    }

    @Override
    public String toString() {
        return "DefaultCareerCheckList{" +
                "id='" + id + '\'' +
                ", checkListTitle='" + checkListTitle + '\'' +
                ", displayOrder=" + displayOrder +
                ", defaultCareerCheckListItems=" + Arrays.toString(defaultCareerCheckListItems) +
                '}';
    }
}
