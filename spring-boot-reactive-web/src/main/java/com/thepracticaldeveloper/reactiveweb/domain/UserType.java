package com.thepracticaldeveloper.reactiveweb.domain;

import java.util.Objects;

public class UserType {

    private String id;
    private String typeName;
    private String typeDescription;

    // Empty constructor is needed for Jackson to deserialize JSON
    public UserType() {
    }

    public UserType(String typeName, String typeDescription) {
        this.typeName = typeName;
        this.typeDescription = typeDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserType userType = (UserType) o;
        return getTypeName().equals(userType.getTypeName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTypeName());
    }

    @Override
    public String toString() {
        return "UserType{" +
                "id='" + id + '\'' +
                ", typeName='" + typeName + '\'' +
                ", typeDescription='" + typeDescription + '\'' +
                '}';
    }
}
