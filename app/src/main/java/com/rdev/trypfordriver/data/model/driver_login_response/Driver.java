package com.rdev.trypfordriver.data.model.driver_login_response;

import com.google.gson.annotations.SerializedName;

public class Driver {

    @SerializedName("email_address")
    private String emailAddress;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("id")
    private int id;

    @SerializedName("middle_name")
    private String middleName;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("about_me")
    private Object aboutMe;

    @SerializedName("mobile_phone_number")
    private String mobilePhoneNumber;

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setAboutMe(Object aboutMe) {
        this.aboutMe = aboutMe;
    }

    public Object getAboutMe() {
        return aboutMe;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    @Override
    public String toString() {
        return
                "Driver{" +
                        "email_address = '" + emailAddress + '\'' +
                        ",last_name = '" + lastName + '\'' +
                        ",id = '" + id + '\'' +
                        ",middle_name = '" + middleName + '\'' +
                        ",first_name = '" + firstName + '\'' +
                        ",about_me = '" + aboutMe + '\'' +
                        ",mobile_phone_number = '" + mobilePhoneNumber + '\'' +
                        "}";
    }
}