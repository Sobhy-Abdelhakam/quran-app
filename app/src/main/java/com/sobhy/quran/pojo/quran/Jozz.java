package com.sobhy.quran.pojo.quran;

public class Jozz {
    int jozzNumber, startPage, endPage, ayahId;
    String firstAyahInJozz;

    public int getAyahId() {
        return ayahId;
    }

    public void setAyahId(int ayahId) {
        this.ayahId = ayahId;
    }

    public int getNumberOfPageOfJozz() {
        return endPage-startPage;
    }



    public String getFirstAyahInJozz() {
        return firstAyahInJozz;
    }

    public void setFirstAyahInJozz(String firstAyahInJozz) {
        this.firstAyahInJozz = firstAyahInJozz;
    }

    public int getJozzNumber() {
        return jozzNumber;
    }

    public void setJozzNumber(int jozzNumber) {
        this.jozzNumber = jozzNumber;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }
}
