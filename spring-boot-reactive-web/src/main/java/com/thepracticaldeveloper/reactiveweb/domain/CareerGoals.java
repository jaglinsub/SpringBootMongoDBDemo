package com.thepracticaldeveloper.reactiveweb.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Document(collection = "CareerGoals")
public final class CareerGoals {

    private String id;
    private Integer currentPoints;
    private Integer goalPoints;
    @DBRef
    private CareerCheckList[] careerCheckList;
    private String userId;

    // Empty constructor is needed for Jackson to deserialize JSON
    public CareerGoals() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(Integer currentPoints) {
        this.currentPoints = currentPoints;
    }

    public Integer getGoalPoints() {
        return goalPoints;
    }

    public void setGoalPoints(Integer goalPoints) {
        this.goalPoints = goalPoints;
    }

    public CareerCheckList[] getCareerCheckList() {
        return careerCheckList;
    }

    public void setCareerCheckList(CareerCheckList[] careerCheckList) {
        this.careerCheckList = careerCheckList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CareerGoals{" +
                "id='" + id + '\'' +
                ", currentPoints=" + currentPoints +
                ", goalPoints=" + goalPoints +
                ", careerCheckList=" + Arrays.toString(careerCheckList) +
                ", userId='" + userId + '\'' +
                '}';
    }
}
