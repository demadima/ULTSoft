package com.example.ultsoft.ui.main.item;

import android.graphics.drawable.Drawable;

public class PhotoItems {
    private Drawable image;
    private String imageName;

    public PhotoItems(Drawable image, String imageName) {
        this.image = image;
        this.imageName = imageName;
    }
    public PhotoItems( String imageName) {
        this.imageName = imageName;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
