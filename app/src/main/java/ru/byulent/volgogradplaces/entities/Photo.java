package ru.byulent.volgogradplaces.entities;

import android.graphics.Bitmap;

public class Photo {
    private Bitmap photo;
    private double latitude;
    private double longtitude;
    private String description;

    public Photo(Bitmap photo, double latitude, double longtitude, String description) {
        this.photo = photo;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.description = description;
    }
}
