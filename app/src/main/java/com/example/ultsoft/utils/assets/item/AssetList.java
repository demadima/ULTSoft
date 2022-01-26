package com.example.ultsoft.utils.assets.item;

import android.graphics.drawable.Drawable;

public class AssetList {
    private String imageName;
    private Drawable image;

    public AssetList(String imageName, Drawable image) {
        this.imageName = imageName;
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
