package com.demo.moneytap.moneytapapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Query implements Serializable{

    @SerializedName("pages")
    private List<Pages> pages;

    @SerializedName("redirects")
    private List<Redirects> redirects;

    public List<Pages> getPages ()
    {
        return pages;
    }

    public void setPages (List<Pages> pages)
    {
        this.pages = pages;
    }

    public List<Redirects> getRedirects ()
    {
        return redirects;
    }

    public void setRedirects (List<Redirects> redirects)
    {
        this.redirects = redirects;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pages = "+pages+", redirects = "+redirects+"]";
    }
}
