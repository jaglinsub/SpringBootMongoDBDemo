package com.thepracticaldeveloper.reactiveweb.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

public final class QualificationDetails {

    private String id;
    private String quatlificationDescription;

    // Empty constructor is needed for Jackson to deserialize JSON
    public QualificationDetails() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuatlificationDescription() {
        return quatlificationDescription;
    }

    public void setQuatlificationDescription(String quatlificationDescription) {
        this.quatlificationDescription = quatlificationDescription;
    }
}
