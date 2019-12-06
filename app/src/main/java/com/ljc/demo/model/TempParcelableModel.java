package com.ljc.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TempParcelableModel implements Parcelable {
    private long id;
    private String content;

    public TempParcelableModel(Parcel in) {
        id = in.readLong();
        content = in.readString();
    }

    public static final Creator<TempParcelableModel> CREATOR = new Creator<TempParcelableModel>() {
        @Override
        public TempParcelableModel createFromParcel(Parcel in) {
            return new TempParcelableModel(in);
        }

        @Override
        public TempParcelableModel[] newArray(int size) {
            return new TempParcelableModel[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(content);
    }
}
