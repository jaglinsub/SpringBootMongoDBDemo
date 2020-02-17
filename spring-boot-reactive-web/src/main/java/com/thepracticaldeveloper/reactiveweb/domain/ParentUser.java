package com.thepracticaldeveloper.reactiveweb.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Parent")
public final class ParentUser {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean emailSent = false;
    private String subcriptionCustId;

    // Empty constructor is needed for Jackson to deserialize JSON
    public ParentUser() {
    }

    public ParentUser(String id) {
        this();
        this.id = id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isEmailSent() {
        return emailSent;
    }

    public void setEmailSent(boolean emailSent) {
        this.emailSent = emailSent;
    }

    public String getSubcriptionCustId() {
        return subcriptionCustId;
    }

    public void setSubcriptionCustId(String subcriptionCustId) {
        this.subcriptionCustId = subcriptionCustId;
    }

    @Override
    public String toString() {
        return "ParentUser{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailSent=" + emailSent +
                ", subcriptionCustId='" + subcriptionCustId + '\'' +
                '}';
    }
}
