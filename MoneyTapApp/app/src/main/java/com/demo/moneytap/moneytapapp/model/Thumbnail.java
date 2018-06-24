package com.demo.moneytap.moneytapapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Thumbnail implements Serializable{

    @SerializedName("height")
    private String height;

    @SerializedName("source")
    private String source;

    @SerializedName("width")
    private String width;

    public String getHeight ()
    {
        return height;
    }

    public void setHeight (String height)
    {
        this.height = height;
    }

    public String getSource ()
    {
        return source;
    }

    public void setSource (String source)
    {
        this.source = source;
    }

    public String getWidth ()
    {
        return width;
    }

    public void setWidth (String width)
    {
        this.width = width;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [height = "+height+", source = "+source+", width = "+width+"]";
    }
}