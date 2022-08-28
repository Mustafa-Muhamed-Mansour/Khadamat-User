package com.khadamat.model;

public class ReservationModel
{
    private String id;
    private String randomKey;
    private String image;
    private String name;
    private String location;
    private String phone;
    private String email;
    private String day;
    private String time;

    public ReservationModel()
    {
    }

    public ReservationModel(String id, String randomKey, String image, String name, String location, String phone, String email, String day, String time)
    {
        this.id = id;
        this.randomKey = randomKey;
        this.image = image;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.day = day;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }
}
