
package com.example.alex.youtubelearningbuddy.model.videos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Thumbnails implements Parcelable {

    @SerializedName("default")
    @Expose
    private Default _default;
    @SerializedName("medium")
    @Expose
    private Medium medium;
    @SerializedName("high")
    @Expose
    private High high;

    public Default getDefault() {
        return _default;
    }

    public void setDefault(Default _default) {
        this._default = _default;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public High getHigh() {
        return high;
    }

    public void setHigh(High high) {
        this.high = high;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this._default, flags);
        dest.writeParcelable(this.medium, flags);
        dest.writeParcelable(this.high, flags);
    }

    public Thumbnails() {
    }

    protected Thumbnails(Parcel in) {
        this._default = in.readParcelable(Default.class.getClassLoader());
        this.medium = in.readParcelable(Medium.class.getClassLoader());
        this.high = in.readParcelable(High.class.getClassLoader());
    }

    public static final Parcelable.Creator<Thumbnails> CREATOR = new Parcelable.Creator<Thumbnails>() {
        @Override
        public Thumbnails createFromParcel(Parcel source) {
            return new Thumbnails(source);
        }

        @Override
        public Thumbnails[] newArray(int size) {
            return new Thumbnails[size];
        }
    };
}
