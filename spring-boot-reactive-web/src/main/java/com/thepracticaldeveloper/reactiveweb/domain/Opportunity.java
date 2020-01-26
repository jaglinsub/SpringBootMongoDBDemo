package com.thepracticaldeveloper.reactiveweb.domain;

import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Opportunity")
public final class Opportunity {

    private String id;
    private String opportunityName;
    private String opportunityShortDesc;
    private String organizationName;
    private String organizationAddress;
    private String whenPosted;
    private String typeofProfOppurtunity;

    @DBRef
    private OpportunityDetails opportunityDetails;

    // Empty constructor is needed for Jackson to deserialize JSON
    public Opportunity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    public String getOpportunityShortDesc() {
        return opportunityShortDesc;
    }

    public void setOpportunityShortDesc(String opportunityShortDesc) {
        this.opportunityShortDesc = opportunityShortDesc;
    }

    public OpportunityDetails getOpportunityDetails() {
        return opportunityDetails;
    }

    public void setOpportunityDetails(OpportunityDetails opportunityDetails) {
        this.opportunityDetails = opportunityDetails;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationAddress() {
        return organizationAddress;
    }

    public void setOrganizationAddress(String organizationAddress) {
        this.organizationAddress = organizationAddress;
    }

    public String getWhenPosted() {
        return whenPosted;
    }

    public void setWhenPosted(String whenPosted) {
        this.whenPosted = whenPosted;
    }

    public String getTypeofProfOppurtunity() {
        return typeofProfOppurtunity;
    }

    public void setTypeofProfOppurtunity(String typeofProfOppurtunity) {
        this.typeofProfOppurtunity = typeofProfOppurtunity;
    }

    @Override
    public String toString() {
        return "Opportunity{" +
                "id='" + id + '\'' +
                ", opportunityName='" + opportunityName + '\'' +
                ", opportunityShortDesc='" + opportunityShortDesc + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", organizationAddress='" + organizationAddress + '\'' +
                ", whenPosted='" + whenPosted + '\'' +
                ", typeofProfOppurtunity='" + typeofProfOppurtunity + '\'' +
//                ", opportunityDetails=" + opportunityDetails.toString() +
                '}';
    }
}
