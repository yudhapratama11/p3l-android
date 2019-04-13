package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("data")
    @Expose
    private User data;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public LoginResponse(User data, Boolean error, String message)
    {
        this.error = error;
        this.data = data;
        this.message = message;

    }

    public User getData() {
        return data;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

//    public void setData(User data) {
//        this.data = data;
//    }
//
//    public void setError(Boolean error) {
//        this.error = error;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
}
