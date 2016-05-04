package com.tinycore.lifecompile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tag {
    @SerializedName("id")
    @Expose
    public Integer Id;

    @SerializedName("name")
    @Expose
    public String Name;
}
