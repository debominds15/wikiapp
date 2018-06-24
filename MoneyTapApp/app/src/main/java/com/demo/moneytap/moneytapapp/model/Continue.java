package com.demo.moneytap.moneytapapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Continue implements Serializable{

    @SerializedName("gpsoffset")
    private String gpsoffset;

    @SerializedName("continue")
    private String conti;

    public String getGpsoffset ()
    {
        return gpsoffset;
    }

    public void setGpsoffset (String gpsoffset)
    {
        this.gpsoffset = gpsoffset;
    }

    public String getContinue ()
    {
        return conti;
    }

    public void setContinue (String conti)
    {
        this.conti = conti;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [gpsoffset = "+gpsoffset+", continue = "+conti+"]";
    }
}
