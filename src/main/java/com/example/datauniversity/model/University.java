package com.example.datauniversity.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="data_University")
public class University {
    @Id
    @Column(name = "NameUniversity")
    private String nameUniversity;
    @Column(name = "State")
    private String state;
    @Column(name = "InstitutionType")
    private String institutionType;
    @Column(name = "PhoneNumber")
    private String phoneNumber;
    @Column(name = "Website")
    private String website;

    public University() {}

    public String getNameUniversity() {
        return nameUniversity;
    }

    public void setNameUniversity(String nameUniversity) {
        this.nameUniversity = nameUniversity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public University(String nameUniversity, String state, String institutionType, String phoneNumber, String website) {
        this.nameUniversity = nameUniversity;
        this.state = state;
        this.institutionType = institutionType;
        this.phoneNumber = phoneNumber;
        this.website = website;
    }
}
