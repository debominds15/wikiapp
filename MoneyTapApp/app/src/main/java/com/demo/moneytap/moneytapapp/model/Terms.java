package com.demo.moneytap.moneytapapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Terms  implements Serializable{

    @SerializedName("description")
    private List<String> description;

    public List<String> getDescription ()
    {
        return description;
    }

    public void setDescription (List<String> description)
    {
        this.description = description;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [description = "+description+"]";
    }
}
