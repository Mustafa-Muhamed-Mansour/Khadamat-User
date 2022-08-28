package com.khadamat.model;

public class WorkEmployeeModel
{
    private String title, image;

    public WorkEmployeeModel()
    {
    }

    public WorkEmployeeModel(String title, String image)
    {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }
}
