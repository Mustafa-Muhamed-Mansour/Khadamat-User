package com.khadamat.model;

public class SliderModel
{

    private String image;
    private String title;

    public SliderModel()
    {
    }

    public SliderModel(String image, String title)
    {
        this.image = image;
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }
}
