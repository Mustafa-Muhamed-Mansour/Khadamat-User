package com.khadamat.model;

import java.io.Serializable;

public class ServicesModel implements Serializable
{

    private String image, title;
    private Boolean star = true;


    public ServicesModel()
    {
    }

    public ServicesModel(String image, String title, Boolean star)
    {
        this.image = image;
        this.title = title;
        this.star = star;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getStar() {
        return star;
    }
}
