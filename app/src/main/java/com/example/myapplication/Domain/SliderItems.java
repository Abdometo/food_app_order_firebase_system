package com.example.myapplication.Domain;

public class SliderItems {

    private String image;

    // Default constructor required for calls to DataSnapshot.getValue(SliderItems.class)
    public SliderItems() {
    }

    // Parameterized constructor
    public SliderItems(String image) {
        this.image = image;
    }

    // Getter and Setter
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
