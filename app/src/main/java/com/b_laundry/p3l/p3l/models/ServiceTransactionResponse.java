package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ServiceTransactionResponse {
    @SerializedName("data")
    @Expose
    private List<ServiceTransaction> data = new ArrayList<>();

    public List<ServiceTransaction> getData() {
        return data;
    }

    public void setData(List<ServiceTransaction> data) {
        this.data = data;
    }
}
