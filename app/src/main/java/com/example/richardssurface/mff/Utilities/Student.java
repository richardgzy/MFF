/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.richardssurface.mff.Utilities;

import java.io.Serializable;

/**
 *
 * @author Richard's Surface
 */
public class Student implements Serializable {

    private Integer sid;
    private String fName;
    private String lName;
    private String dob;
    private String gender;
    private String studyMode;
    private String course;
    private String address;
    private String subrub;
    private String nationality;
    private String nativeLanguage;
    private String favouriteSport;
    private String favouriteMovie;
    private String favouriteUnit;
    private String currentJob;
    private String monashEmail;
    private String password;
    private String subscriptionDate;
    private String subscriptionTime;

    public Student() {
    }

    public Student(Integer sid) {
        this.sid = sid;
    }

    public Student(Integer sid, String fName, String lName) {
        this.sid = sid;
        this.fName = fName;
        this.lName = lName;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStudyMode() {
        return studyMode;
    }

    public void setStudyMode(String studyMode) {
        this.studyMode = studyMode;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubrub() {
        return subrub;
    }

    public void setSubrub(String subrub) {
        this.subrub = subrub;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    public String getFavouriteSport() {
        return favouriteSport;
    }

    public void setFavouriteSport(String favouriteSport) {
        this.favouriteSport = favouriteSport;
    }

    public String getFavouriteMovie() {
        return favouriteMovie;
    }

    public void setFavouriteMovie(String favouriteMovie) {
        this.favouriteMovie = favouriteMovie;
    }

    public String getFavouriteUnit() {
        return favouriteUnit;
    }

    public void setFavouriteUnit(String favouriteUnit) {
        this.favouriteUnit = favouriteUnit;
    }

    public String getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }

    public String getMonashEmail() {
        return monashEmail;
    }

    public void setMonashEmail(String monashEmail) {
        this.monashEmail = monashEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(String subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public String getSubscriptionTime() {
        return subscriptionTime;
    }

    public void setSubscriptionTime(String subscriptionTime) {
        this.subscriptionTime = subscriptionTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sid != null ? sid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.sid == null && other.sid != null) || (this.sid != null && !this.sid.equals(other.sid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MFFRest.Student[ sid=" + sid + " ]";
    }
    
    //to String format: sid, subrub, nationality, native language, favourite sport, favourite movie, favourite unit, currentJob
    public String toSearchingString(){
        return sid + "," + subrub + "," + nationality + "," + nativeLanguage + "," + favouriteSport + "," + favouriteMovie + "," + favouriteUnit + "," + currentJob;
    }
    
}