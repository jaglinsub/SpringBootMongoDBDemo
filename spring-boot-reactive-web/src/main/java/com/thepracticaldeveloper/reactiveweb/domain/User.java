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
        this.userType = userType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId().equals(user.getId()) &&
                getFirstName().equals(user.getFirstName()) &&
                getLastName().equals(user.getLastName()) &&
                getEmail().equals(user.getEmail()) &&
                getDateofBirth().equals(user.getDateofBirth()) &&
                getGrade().equals(user.getGrade()) &&
                Objects.equals(getLocation(), user.getLocation()) &&
                getPhoneNumber().equals(user.getPhoneNumber()) &&
                Objects.equals(getOrganizationName(), user.getOrganizationName()) &&
                getUserType().equals(user.getUserType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getEmail(), getDateofBirth(), getGrade(), getLocation(), getPhoneNumber(), getOrganizationName(), getUserType());
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
                ", userType=" + userType +
                '}';
    }
}
