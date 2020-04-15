package com.temp.ticat2.ui.dashboard;

import java.sql.Timestamp;
import java.util.Date;

public class Screen {
    private int sid;
    private int duration;
    private Timestamp dateTime;
    private String language;
    private String dType;
    private int hall;
    private String hType;
    private double price;

    public Screen(){

    }

    public Screen(int duration,Timestamp time,String language,String dType,int hall,String hType,double price){
        this.duration = duration;
        this.dateTime = time;
        this.language = language;
        this.dType = dType;
        this.hall = hall;
        this.hType = hType;
        this.price = price;
    }

    public Screen(int duration,String language,String dType,int hall,String hType,double price){
        this.duration = duration;
        this.language = language;
        this.dType = dType;
        this.hall = hall;
        this.hType = hType;
        this.price = price;
    }

    public Screen(String language,String dType,int hall,double price){
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

    public Timestamp getTime(){
        return dateTime;
    }

    public String gethType() {
        return hType;
    }

    public String getLanguage() {
        return language;
    }

    public String getDType() {
        return dType;
    }

    public int getHall() {
        return hall;
    }

    public double getPrice() {
        return price;
    }
}
