package com.thepracticaldeveloper.reactiveweb.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

@Document(collection = "User")
public final class User {

    private String id;
    private String firstName;
    private String lastName;
    private String email;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dateofBirth;
    private String grade;
    private String location;
    private String phoneNumber;
    private String organizationName;
    private String expYearofGrad;
    private boolean receiveUpdates;
    private String subscriptionId;
    private String subscriptionPlanId;
    private String subscriptionPlanName;

    @DBRef
    private ParentUser parentUser;

    @DBRef
    private UserType userType;

    // Empty constructor is needed for Jackson to deserialize JSON
    public User() {
    }

    public User(String firstName, String lastName, String email, Date dateofBirth, String grade, String location, String phoneNumber, String organizationName, UserType userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateofBirth = dateofBirth;
        this.grade = grade;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.organizationName = organizationName;
//        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(Date dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isReceiveUpdates() {
        return receiveUpdates;
    }

    public void setReceiveUpdates(boolean receiveUpdates) {
        this.receiveUpdates = receiveUpdates;
    }

    public String getExpYearofGrad() {
        return expYearofGrad;
    }

    public void setExpYearofGrad(String expYearofGrad) {
        this.expYearofGrad = expYearofGrad;
    }

    public ParentUser getParentUser() {
        return parentUser;
    }

    public void setParentUser(ParentUser parentUser) {
        this.parentUser = parentUser;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getSubscriptionPlanId() {
        return subscriptionPlanId;
    }

    public void setSubscriptionPlanId(String subscriptionPlanId) {
        this.subscriptionPlanId = subscriptionPlanId;
    }

    public String getSubscriptionPlanName() {
        return subscriptionPlanName;
    }

    public void setSubscriptionPlanName(String subscriptionPlanName) {
        this.subscriptionPlanName = subscriptionPlanName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dateofBirth=" + dateofBirth +
                ", grade='" + grade + '\'' +
                ", location='" + location + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", expYearofGrad='" + expYearofGrad + '\'' +
                ", receiveUpdates=" + receiveUpdates +
                ", subscriptionId='" + subscriptionId + '\'' +
                ", subscriptionPlanId='" + subscriptionPlanId + '\'' +
                ", subscriptionPlanName='" + subscriptionPlanName + '\'' +
                ", parentUser=" + parentUser +
                ", userType=" + userType +
                '}';
    }
}
