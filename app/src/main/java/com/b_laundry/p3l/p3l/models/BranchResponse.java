package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BranchResponse {
    @SerializedName("data")
    @Expose
    private List<Branch> data = null;

    public List<Branch> getData() {
        return data;
    }

    public void setData(List<Branch> data) {
        this.data = data;
    }

}
