package com.thepracticaldeveloper.reactiveweb.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.Objects;

@Document(collection = "Profile")
public final class Profile {

    private String id;
    private String classOf;
    private String highSchoolName;
    private String iWantToBe;
    private String moreAboutMe;
    private Experience[] experienceArr;

    private String userId;

    // Empty constructor is needed for Jackson to deserialize JSON
    public Profile() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassOf() {
        return classOf;
    }

    public void setClassOf(String classOf) {
        this.classOf = classOf;
    }

    public String getHighSchoolName() {
        return highSchoolName;
    }

    public void setHighSchoolName(String highSchoolName) {
        this.highSchoolName = highSchoolName;
    }

    public String getiWantToBe() {
        return iWantToBe;
    }

    public void setiWantToBe(String iWantToBe) {
        this.iWantToBe = iWantToBe;
    }

    public String getMoreAboutMe() {
        return moreAboutMe;
    }

    public void setMoreAboutMe(String moreAboutMe) {
        this.moreAboutMe = moreAboutMe;
    }

    public Experience[] getExperienceArr() {
        return experienceArr;
    }

    public void setExperienceArr(Experience[] experienceArr) {
        this.experienceArr = experienceArr;
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
        Profile profile = (Profile) o;
        return getId().equals(profile.getId()) &&
                getClassOf().equals(profile.getClassOf()) &&
                getHighSchoolName().equals(profile.getHighSchoolName()) &&
                getiWantToBe().equals(profile.getiWantToBe()) &&
                getMoreAboutMe().equals(profile.getMoreAboutMe()) &&
                Arrays.equals(getExperienceArr(), profile.getExperienceArr()) &&
                getUserId().equals(profile.getUserId());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getClassOf(), getHighSchoolName(), getiWantToBe(), getMoreAboutMe(), getUserId());
        result = 31 * result + Arrays.hashCode(getExperienceArr());
        return result;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id='" + id + '\'' +
                ", classOf='" + classOf + '\'' +
                ", highSchoolName='" + highSchoolName + '\'' +
                ", iWantToBe='" + iWantToBe + '\'' +
                ", moreAboutMe='" + moreAboutMe + '\'' +
                ", experienceArr=" + Arrays.toString(experienceArr) +
                ", userId='" + userId + '\'' +
                '}';
    }
}
