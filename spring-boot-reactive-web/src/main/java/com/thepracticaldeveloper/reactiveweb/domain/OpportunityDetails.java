package com.thepracticaldeveloper.reactiveweb.domain;

import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "OpportunityDetails")
public final class OpportunityDetails {

    private String id;
    private String opportunityDtlsName;
    private String organizationImage;
    private String organizationURL;
    private String typeofProfOppurtunity;
    private String workLength;
    private String workHours;
    private String recommendations;
    private String schoolAttendance;
    private String gpaScore;
    private String oppurtunityLongDescription;
//    private QualificationDetails[] qualificationDetails;
    private String[] qualificationDetails;


    // Empty constructor is needed for Jackson to deserialize JSON
    public OpportunityDetails() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpportunityDtlsName() {
        return opportunityDtlsName;
    }

    public void setOpportunityDtlsName(String opportunityDtlsName) {
        this.opportunityDtlsName = opportunityDtlsName;
    }

    public String getOrganizationImage() {
        return organizationImage;
    }

    public void setOrganizationImage(String organizationImage) {
        this.organizationImage = organizationImage;
    }

    public String getOrganizationURL() {
        return organizationURL;
    }

    public void setOrganizationURL(String organizationURL) {
        this.organizationURL = organizationURL;
    }

    public String getTypeofProfOppurtunity() {
        return typeofProfOppurtunity;
    }

    public void setTypeofProfOppurtunity(String typeofProfOppurtunity) {
        this.typeofProfOppurtunity = typeofProfOppurtunity;
    }

    public String getWorkLength() {
        return workLength;
    }

    public void setWorkLength(String workLength) {
        this.workLength = workLength;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getSchoolAttendance() {
        return schoolAttendance;
    }

    public void setSchoolAttendance(String schoolAttendance) {
        this.schoolAttendance = schoolAttendance;
    }

    public String getGpaScore() {
        return gpaScore;
    }

    public void setGpaScore(String gpaScore) {
        this.gpaScore = gpaScore;
    }

    public String getOppurtunityLongDescription() {
        return oppurtunityLongDescription;
    }

    public void setOppurtunityLongDescription(String oppurtunityLongDescription) {
        this.oppurtunityLongDescription = oppurtunityLongDescription;
    }

    public String[] getQualificationDetails() {
        return qualificationDetails;
    }

    public void setQualificationDetails(String[] qualificationDetails) {
        this.qualificationDetails = qualificationDetails;
    }
}
