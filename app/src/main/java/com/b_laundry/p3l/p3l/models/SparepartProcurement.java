package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SparepartProcurement {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sales")
    @Expose
    private String sales;
    @SerializedName("id_sales")
    @Expose
    private Integer idSales;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("detail")
    @Expose
    private SparepartProcurementDetail detail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public Integer getIdSales() {
        return idSales;
    }

    public void setIdSales(Integer idSales) {
        this.idSales = idSales;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public SparepartProcurementDetail getDetail() {
        return detail;
    }

    public void setDetail(SparepartProcurementDetail detail) {
        this.detail = detail;
    }


}
