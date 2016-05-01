package com.tinycore.lifecompile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Token {

    @SerializedName("token")
    @Expose
    public String Token;

    @SerializedName("success")
    @Expose
    public boolean Success;

    @SerializedName("message")
    @Expose
    public String Message;
}