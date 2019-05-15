package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CustomerMotorcycleResponse {
    @SerializedName("data")
    @Expose
    private List<CustomerMotorcycle> data = new ArrayList<>();

    public List<CustomerMotorcycle> getData() {
        return data;
    }

    public void setData(List<CustomerMotorcycle> data) {
        this.data = data;
    }
}
