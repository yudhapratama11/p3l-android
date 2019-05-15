package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistorySparepart {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_sparepart")
    @Expose
    private String idSparepart;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("jumlah")
    @Expose
    private Integer jumlah;
    @SerializedName("satuan_harga")
    @Expose
    private Integer satuanHarga;
    @SerializedName("subtotal")
    @Expose
    private Integer subtotal;
    @SerializedName("status")
    @Expose
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdSparepart() {
        return idSparepart;
    }

    public void setIdSparepart(String idSparepart) {
        this.idSparepart = idSparepart;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Integer getSatuanHarga() {
        return satuanHarga;
    }

    public void setSatuanHarga(Integer satuanHarga) {
        this.satuanHarga = satuanHarga;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
