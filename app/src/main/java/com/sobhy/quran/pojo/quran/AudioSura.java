package com.sobhy.quran.pojo.quran;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class AudioSura implements Parcelable {
    int suraNumber;

    boolean isDownloading;

    public boolean isDownloading() {
        return isDownloading;
    }

    public void setDownloading(boolean downloading) {
        isDownloading = downloading;
    }

    String suraUrl, suraName, reciterName, moshafName;

    public AudioSura() {
    }

    public AudioSura(int suraNumber, String suraUrl, String suraName, String reciterName, String moshafName) {
        this.suraNumber = suraNumber;
        this.suraUrl = suraUrl;
        this.suraName = suraName;
        this.reciterName = reciterName;
        this.moshafName = moshafName;
    }

    public int getSuraNumber() {
        return suraNumber;
    }

    public void setSuraNumber(int suraNumber) {
        this.suraNumber = suraNumber;
    }

    public String getSuraUrl() {
        return suraUrl;
    }

    public void setSuraUrl(String suraUrl) {
        this.suraUrl = suraUrl;
    }

    public String getSuraName() {
        return suraName;
    }

    public void setSuraName(String suraName) {
        this.suraName = suraName;
    }

    public String getReciterName() {
        return reciterName;
    }

    public void setReciterName(String reciterName) {
        this.reciterName = reciterName;
    }

    public String getMoshafName() {
        return moshafName;
    }

    public void setMoshafName(String moshafName) {
        this.moshafName = moshafName;
    }


    protected AudioSura(Parcel in){
        suraNumber = in.readInt();
        suraUrl = in.readString();
        suraName = in.readString();
        reciterName = in.readString();
        moshafName = in.readString();
    }

    public static final Creator<AudioSura> CREATOR = new Creator<AudioSura>() {
        @Override
        public AudioSura createFromParcel(Parcel in) {
            return new AudioSura(in);
        }

        @Override
        public AudioSura[] newArray(int size) {
            return new AudioSura[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(suraNumber);
        dest.writeString(suraUrl);
        dest.writeString(suraName);
        dest.writeString(reciterName);
        dest.writeString(moshafName);
    }
}
