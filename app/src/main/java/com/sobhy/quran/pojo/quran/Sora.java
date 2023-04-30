package com.sobhy.quran.pojo.quran;


public class Sora {
    int soraNumber, startPage, endPage, numbersOfAyat;
    String arabicName;

    public int getSoraNumber() {
        return soraNumber;
    }

    public void setSoraNumber(int soraNumber) {
        this.soraNumber = soraNumber;
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

    public int getNumbersOfAyat() {
        return numbersOfAyat;
    }

    public void setNumbersOfAyat(int numbersOfAyat) {
        this.numbersOfAyat = numbersOfAyat;
    }

    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }

//    public String getEnglishName() {
//        return EnglishName;
//    }
//
//    public void setEnglishName(String englishName) {
//        EnglishName = englishName;
//    }
}
