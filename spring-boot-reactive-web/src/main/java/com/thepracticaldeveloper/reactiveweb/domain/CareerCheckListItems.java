package com.thepracticaldeveloper.reactiveweb.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CareerCheckListItems")
public final class CareerCheckListItems extends DefaultCareerCheckListItems  {

    //private String id;
    private boolean isSelected;
//    @DBRef
//    private DefaultCareerCheckListItems defaultCareerCheckListItems;
    private String userId;

    // Empty constructor is needed for Jackson to deserialize JSON
    public CareerCheckListItems() {
        super();
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

//    public DefaultCareerCheckListItems getDefaultCareerCheckListItems() {
//        return defaultCareerCheckListItems;
//    }
//
//    public void setDefaultCareerCheckListItems(DefaultCareerCheckListItems defaultCareerCheckListItems) {
//        this.defaultCareerCheckListItems = defaultCareerCheckListItems;
//    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CareerCheckListItems{" +
                //"id='" + id + '\'' +
                ", isSelected=" + isSelected +
               // ", defaultCareerCheckListItems=" + defaultCareerCheckListItems +
                ", userId='" + userId + '\'' +
                '}';
    }
}
