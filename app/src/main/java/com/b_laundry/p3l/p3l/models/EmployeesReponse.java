package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmployeesReponse {
    @SerializedName("data")
    @Expose
    private List<Employees> data = null;

    public List<Employees> getData() {
        return data;
    }

    public void setData(List<Employees> data) {
        this.data = data;
    }
}
