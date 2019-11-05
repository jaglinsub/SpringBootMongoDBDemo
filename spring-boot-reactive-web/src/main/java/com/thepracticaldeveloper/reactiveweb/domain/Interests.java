package com.thepracticaldeveloper.reactiveweb.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Document(collection = "Interests")
public final class Interests {

    private String id;
    private InterestOptions[] interestOptions;


    private String userId;

    // Empty constructor is needed for Jackson to deserialize JSON
    public Interests() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InterestOptions[] getInterestOptions() {
        return interestOptions;
    }

    public void setInterestOptions(InterestOptions[] interestOptions) {
        this.interestOptions = interestOptions;
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
        Interests interests = (Interests) o;
        return getId().equals(interests.getId()) &&
                Arrays.equals(getInterestOptions(), interests.getInterestOptions()) &&
                Objects.equals(getUserId(), interests.getUserId());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getUserId());
        result = 31 * result + Arrays.hashCode(getInterestOptions());
        return result;
    }

    @Override
    public String toString() {
        return "Interests{" +
                "id='" + id + '\'' +
                ", interestOptions=" + Arrays.toString(interestOptions) +
                ", userId=" + userId +
                '}';
    }
}
