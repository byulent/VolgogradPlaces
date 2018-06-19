package ru.byulent.volgogradplaces.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import com.mongodb.BasicDBList;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ru.byulent.volgogradplaces.util.Common;

public class Photo implements Parcelable{
    private String id;
    private String title;
    private String[] keywords;
    private Bitmap photo;
    private double latitude;
    private double longitude;
    private String description;

    public Photo(String id, String title, String[] keywords, Bitmap photo, double latitude, double longitude, String description) {
        this.id = id;
        this.title = title;
        this.keywords = keywords;
        this.photo = photo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public Photo(Document document) {
        try {
            id = document.get("_id", ObjectId.class).toHexString();
            title = document.getString("titlePhoto");
            String folder = "http://" + Common.HOST_NAME + document.get("medium", String.class).substring(1);
//                Log.d("obj", folder);
            InputStream stream = new URL(folder).openStream();
            List kw = document.get("kwPhoto", ArrayList.class);
            keywords = (String[]) kw.toArray(new String[kw.size()]);
            photo = BitmapFactory.decodeStream(stream);
            latitude = document.getDouble("latit");
            longitude = document.getDouble("longit");
            description = document.getString("desc");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Photo(Parcel in) {
        id = in.readString();
        title = in.readString();
        keywords = in.createStringArray();
        photo = in.readParcelable(Bitmap.class.getClassLoader());
        latitude = in.readDouble();
        longitude = in.readDouble();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeStringArray(keywords);
        dest.writeParcelable(photo, flags);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public String getDescription() {
        return description;
    }
}
