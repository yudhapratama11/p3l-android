package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaction {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("status_paid")
    @Expose
    private Integer statusPaid;
    @SerializedName("id_transaction_type")
    @Expose
    private Integer idTransactionType;
    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("id_customer")
    @Expose
    private Integer idCustomer;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("subtotal")
    @Expose
    private Integer subtotal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getStatusPaid() {
        return statusPaid;
    }

    public void setStatusPaid(Integer statusPaid) {
        this.statusPaid = statusPaid;
    }

    public Integer getIdTransactionType() {
        return idTransactionType;
    }

    public void setIdTransactionType(Integer idTransactionType) {
        this.idTransactionType = idTransactionType;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }
}
