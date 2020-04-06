package com.temp.ticat2.ui.dashboard;

import java.util.Date;

public class Screen {
    private int sid;
    private int duration;
    private Date time;
    private String language;
    private String dType;
    private String hall;
    private double price;

    public Screen(){

    }

    public Screen(int sid,int duration,Date time,String language,String dType,String hall,double price){
        this.sid = sid;
        this.duration = duration;
        this.time = time;
        this.language = language;
        this.dType = dType;
        this.hall = hall;
        this.price = price;
    }

    public Screen(int duration,Date time,String language,String dType,String hall,double price){
        this.duration = duration;
        this.time = time;
        this.language = language;
        this.dType = dType;
        this.hall = hall;
        this.price = price;
    }

    public Screen(String language,String dType,String hall,double price){
        this.language = language;
        this.dType = dType;
        this.hall = hall;
        this.price = price;
    }

    public int getSid(){
        return sid;
    }

    public int getDuration(){
        return duration;
    }

    public Date getTime(){
        return time;
    }

    public String getLanguage() {
        return language;
    }

    public String getDType() {
        return dType;
    }

    public String getHall() {
        return hall;
    }

    public double getPrice() {
        return price;
    }
}
