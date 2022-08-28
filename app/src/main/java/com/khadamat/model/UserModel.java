package com.khadamat.model;

public class UserModel
{

    private String randomKey;
    private String id;
    private String image;
    private String firstName;
    private String lastName;
    private String location;
    private String phoneNumber;
    private String email;
    private String gender;

    public UserModel()
    {
    }

    public UserModel(String randomKey, String id, String image, String firstName, String lastName, String location, String phoneNumber, String email, String gender)
    {
        this.randomKey = randomKey;
        this.id = id;
        this.image = image;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
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

    public String getLocation() {
        return location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }
}
