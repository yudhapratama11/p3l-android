package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import okhttp3.MultipartBody;

public class Sparepart {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("merk")
    @Expose
    private String merk;
    @SerializedName("harga_beli")
    @Expose
    private String hargaBeli;
    @SerializedName("harga_jual")
    @Expose
    private String hargaJual;
    @SerializedName("stok")
    @Expose
    private String stok;
    @SerializedName("stok_minimal")
    @Expose
    private String stokMinimal;
    @SerializedName("penempatan")
    @Expose
    private String penempatan;
    @SerializedName("gambar")
    @Expose
    private String gambar;
    @SerializedName("tipe_sparepart")
    @Expose
    private String tipeSparepart;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(String hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public String getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(String hargaJual) {
        this.hargaJual = hargaJual;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getStokMinimal() {
        return stokMinimal;
    }

    public void setStokMinimal(String stokMinimal) {
        this.stokMinimal = stokMinimal;
    }

    public String getPenempatan() {
        return penempatan;
    }

    public void setPenempatan(String penempatan) {
        this.penempatan = penempatan;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getTipeSparepart() {
        return tipeSparepart;
    }

    public void setTipeSparepart(String tipeSparepart) {
        this.tipeSparepart = tipeSparepart;
    }

}
