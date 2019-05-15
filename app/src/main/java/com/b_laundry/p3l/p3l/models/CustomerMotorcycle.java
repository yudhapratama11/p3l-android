package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerMotorcycle {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("plat")
    @Expose
    private String plat;
    @SerializedName("id_motorcycle_type")
    @Expose
    private Integer idMotorcycleType;
    @SerializedName("motorcycle_type")
    @Expose
    private String motorcycleType;
    @SerializedName("id_motorcycle_brand")
    @Expose
    private Integer idMotorcycleBrand;
    @SerializedName("motorcycle_brand")
    @Expose
    private String motorcycleBrand;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlat() {
        return plat;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    public Integer getIdMotorcycleType() {
        return idMotorcycleType;
    }

    public void setIdMotorcycleType(Integer idMotorcycleType) {
        this.idMotorcycleType = idMotorcycleType;
    }

    public String getMotorcycleType() {
        return motorcycleType;
    }

    public void setMotorcycleType(String motorcycleType) {
        this.motorcycleType = motorcycleType;
    }

    public Integer getIdMotorcycleBrand() {
        return idMotorcycleBrand;
    }

    public void setIdMotorcycleBrand(Integer idMotorcycleBrand) {
        this.idMotorcycleBrand = idMotorcycleBrand;
    }

    public String getMotorcycleBrand() {
        return motorcycleBrand;
    }

    public void setMotorcycleBrand(String motorcycleBrand) {
        this.motorcycleBrand = motorcycleBrand;
    }
}
