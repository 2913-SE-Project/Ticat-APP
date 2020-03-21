package com.temp.ticat2.ui.home;

import java.sql.Date;

public class Movies {
    private int id;
    private String name;
    private String director;
    private String mType;
    private String language;
    private String country;
    private int runningTime;
    private String dType;
    private String info;
    private String poster;
    private Date releaseDate;

    public Movies(String name, String poster, Date releaseDate){
        this.name = name;
        this.poster = poster;
        this.releaseDate = releaseDate;

    }

    public String getName() {
        return name;
    }


}
