package com.temp.ticat2.ui.home;

import java.sql.Date;

public class Movie {
    private int id;
    private String name;
    private String director;
    private String mType;
    private String language;
    private String country;
    private int runningTime;
    private String dType;
    private String info;
    private int posterId;
    private Date releaseDate;

    public Movie(String name, int posterId){
        this.name = name;
        this.posterId = posterId;

    }

    public String getName() {
        return name;
    }

    public int getPoster(){
        return posterId;
    }

}
