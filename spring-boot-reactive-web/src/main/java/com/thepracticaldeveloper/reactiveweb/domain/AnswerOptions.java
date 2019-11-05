package com.thepracticaldeveloper.reactiveweb.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "AnswerOptions")
public final class AnswerOptions {

    private String id;
    private String answerName;
    private String answerDescription;

    // Empty constructor is needed for Jackson to deserialize JSON
    public AnswerOptions() {
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswerName() {
        return answerName;
    }

    public void setAnswerName(String answerName) {
        this.answerName = answerName;
    }

    public String getAnswerDescription() {
        return answerDescription;
    }

    public void setAnswerDescription(String answerDescription) {
        this.answerDescription = answerDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerOptions that = (AnswerOptions) o;
        return getId().equals(that.getId()) &&
                getAnswerName().equals(that.getAnswerName()) &&
                Objects.equals(getAnswerDescription(), that.getAnswerDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAnswerName(), getAnswerDescription());
    }

    @Override
    public String toString() {
        return "AnswerOptions{" +
                "id='" + id + '\'' +
                ", answerName='" + answerName + '\'' +
                ", answerDescription='" + answerDescription + '\'' +
                '}';
    }
}
