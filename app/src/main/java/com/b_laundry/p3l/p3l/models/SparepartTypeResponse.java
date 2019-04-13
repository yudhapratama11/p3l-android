package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SparepartTypeResponse {
    @SerializedName("data")
    @Expose
    private List<SparepartType> data = null;

    public List<SparepartType> getData() {
        return data;
    }

    public void setData(List<SparepartType> data) {
        this.data = data;
    }
}
