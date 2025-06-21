package com.birthday.video.maker.activities;

import android.os.Parcel;
import android.os.Parcelable;

public class Media implements Parcelable {
    private String uriString;
    private String name;
    private Long id;

    public Media(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUriString() {
        return uriString;
    }

    public void setUriString(String uriString) {
        this.uriString = uriString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Media(Parcel in) {
        uriString= in.readString();
        name = in.readString();
        id = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uriString);
        parcel.writeString(name);
        if(id!= null)
            parcel.writeLong(id);
    }
    public static final Creator<Media> CREATOR = new Creator<Media>() {

        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        public Media[] newArray(int size) {
            return new Media[size];
        }
    };
}