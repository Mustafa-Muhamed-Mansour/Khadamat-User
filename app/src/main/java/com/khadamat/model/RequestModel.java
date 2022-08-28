package com.khadamat.model;

public class RequestModel
{

    private String randomKey;
    private String id;
    private String image;
    private String firstName;
    private String lastName;
    private String job;
    private String location;
    private String email;
    private String phoneNumber;
    private String radio;
    private String reason;


    public RequestModel()
    {
    }

    public RequestModel(String randomKey, String id, String image, String firstName, String lastName, String job, String location, String email, String phoneNumber, String radio, String reason)
    {
        this.randomKey = randomKey;
        this.id = id;
        this.image = image;
        this.firstName = firstName;
        this.lastName = lastName;
        this.job = job;
        this.location = location;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.radio = radio;
        this.reason = reason;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getJob() {
        return job;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRadio() {
        return radio;
    }

    public String getReason() {
        return reason;
    }
}
