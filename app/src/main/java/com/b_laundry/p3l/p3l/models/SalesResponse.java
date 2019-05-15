package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SalesResponse {
    @SerializedName("data")
    @Expose
    private List<Sales> data = new ArrayList<>();

    public List<Sales> getData() {
        return data;
    }

    public void setData(List<Sales> data) {
        this.data = data;
    }
}
