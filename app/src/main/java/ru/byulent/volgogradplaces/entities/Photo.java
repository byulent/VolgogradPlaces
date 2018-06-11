package ru.byulent.volgogradplaces.entities;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Photo implements Parcelable{
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

    public Photo() {

    }

    protected Photo(Parcel in) {
        photo = in.readParcelable(Bitmap.class.getClassLoader());
        latitude = in.readDouble();
        longtitude = in.readDouble();
        description = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(photo, flags);
        dest.writeDouble(latitude);
        dest.writeDouble(longtitude);
        dest.writeString(description);
    }
}
