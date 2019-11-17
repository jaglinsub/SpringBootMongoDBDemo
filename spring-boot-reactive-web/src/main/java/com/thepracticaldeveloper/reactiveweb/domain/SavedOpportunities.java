package com.thepracticaldeveloper.reactiveweb.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SavedOpportunities")
public final class SavedOpportunities {

    private String id;
    private String oppurtunityId;
    private String userId;

    // Empty constructor is needed for Jackson to deserialize JSON
    public SavedOpportunities() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOppurtunityId() {
        return oppurtunityId;
    }

    public void setOppurtunityId(String oppurtunityId) {
        this.oppurtunityId = oppurtunityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
