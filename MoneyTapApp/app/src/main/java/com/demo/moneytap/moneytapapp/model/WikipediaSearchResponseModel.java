package com.demo.moneytap.moneytapapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WikipediaSearchResponseModel implements Serializable{

    @SerializedName("query")
    private Query query;

    @SerializedName("continue")
    private Continue conti;

    @SerializedName("batchcomplete")
    private String batchcomplete;

    public Query getQuery ()
    {
        return query;
    }

    public void setQuery (Query query)
    {
        this.query = query;
    }

    public Continue getContinue ()
    {
        return conti;
    }

    public void setContinue (Continue conti)
    {
        this.conti = conti;
    }

    public String getBatchcomplete ()
    {
        return batchcomplete;
    }

    public void setBatchcomplete (String batchcomplete)
    {
        this.batchcomplete = batchcomplete;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [query = "+query+", continue = "+conti+", batchcomplete = "+batchcomplete+"]";
    }
}