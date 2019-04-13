package com.b_laundry.p3l.p3l.models;

import java.util.List;
import java.util.Map;

public class User {
    private int id;
    private String username, nama, role, branch;

    public User(int id, String username, String nama, String role, String branch)
    {
        this.id = id;
        this.username = username;
        this.nama = nama;
        this.role = role;
        this.branch = branch;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNama() {
        return nama;
    }

    public String getRole() {
        return role;
    }

    public String getBranch() {
        return branch;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }




}
