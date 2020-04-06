package com.temp.ticat2.ui.home;


import java.util.Date;

public class Movie {
    private int mid;
    private String name;
    private String director;
    private String mType;
    private String language;
    private String country;
    private int runningTime;
    private Date releaseDate;
    private String dType;
    private String intro;
    private int posterId;
    private boolean isFavorite;

    public Movie(int mid, String name, String director, int posterId){
        this.mid = mid;
        this.name = name;
        this.director = director;
        this.posterId = posterId;
    }

    public Movie(int mid, String name, String director,Date releaseDate, int posterId){
        this.mid = mid;
        this.name = name;
        this.director = director;
        this.releaseDate = releaseDate;
        this.posterId = posterId;
    }

    public Movie(int mid, String name, String director,String mType, String language, String country,
                 int runningTime, Date releaseDate, String dType, String intro, int posterId){
        this.mid = mid;
        this.name = name;
        this.director = director;
        this.mType = mType;
        this.language = language;
        this.country = country;
        this.runningTime = runningTime;
        this.releaseDate = releaseDate;
        this.dType = dType;
        this.intro = intro;
        this.posterId = posterId;
    }

    public int getMid(){
        return mid;
    }

    public String getName() {
        return name;
    }

    public String getDirector() {
        return director;
    }

    public String getMType() {
        return mType;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getDType() {
        return dType;
    }

    public String getIntro() {
        return intro;
    }

    public int getPoster(){
        return posterId;
    }

}
