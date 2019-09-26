package com.osaigbovo.myadviser.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Advice {

    @SerializedName("fortune")
    @Expose
    private List<String> fortune = null;

    /**
     * No args constructor for use in serialization
     */
    public Advice() {
    }

    public Advice(List<String> fortune) {
        super();
        this.fortune = fortune;
    }

    public List<String> getFortune() {
        return fortune;
    }

    public void setFortune(List<String> fortune) {
        this.fortune = fortune;
    }

}