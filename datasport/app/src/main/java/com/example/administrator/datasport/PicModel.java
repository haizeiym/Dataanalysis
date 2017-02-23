package com.example.administrator.datasport;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/2/22.
 */
public class PicModel extends BaseObservable implements Parcelable {
    private String picName;

    public PicModel() {
    }

    @Bindable
    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
        notifyPropertyChanged(com.example.administrator.datasport.BR.picName);
    }

    protected PicModel(Parcel in) {
        picName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(picName);
    }

    public static final Creator<PicModel> CREATOR = new Creator<PicModel>() {
        @Override
        public PicModel createFromParcel(Parcel in) {
            return new PicModel(in);
        }

        @Override
        public PicModel[] newArray(int size) {
            return new PicModel[size];
        }
    };
}
