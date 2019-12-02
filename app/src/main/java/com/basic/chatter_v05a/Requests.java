package com.basic.chatter_v05a;


public class Requests {

    public String date;

    public Requests(){

    }

    public Requests(String date){
        this.date = date;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }
}