package com.demo.moneytap.moneytapapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pages implements Serializable{
    @SerializedName("index")
    private String index;

    @SerializedName("title")
    private String title;

    @SerializedName("extract")
    private String extract;

    @SerializedName("ns")
    private String ns;

    @SerializedName("terms")
    private Terms terms;

    @SerializedName("pageid")
    private String pageid;

    @SerializedName("thumbnail")
    private Thumbnail thumbnail;

    public String getIndex ()
    {
        return index;
    }

    public void setIndex (String index)
    {
        this.index = index;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getNs ()
    {
        return ns;
    }

    public void setNs (String ns)
    {
        this.ns = ns;
    }

    public Terms getTerms ()
    {
        return terms;
    }

    public void setTerms (Terms terms)
    {
        this.terms = terms;
    }

    public String getPageid ()
    {
        return pageid;
    }

    public void setPageid (String pageid)
    {
        this.pageid = pageid;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getExtract() {
        return extract;
    }

    public void setExtract(String extract) {
        this.extract = extract;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [index = "+index+", title = "+title+", ns = "+ns+", terms = "+terms+", pageid = "+pageid+
                ", thumbnail = "+thumbnail+
                ", extract = "+extract+
                "]";
    }
}