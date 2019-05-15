package com.b_laundry.p3l.p3l.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static String BASE_URL = "http://192.168.100.145:8000/api/";
    private static RetrofitClient mInstance;
    private static Retrofit retrofit3;
    private Retrofit retrofit;


    private RetrofitClient() {
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(
//                        new Interceptor() {
//                            @Override
//                            public Response intercept(Chain chain) throws IOException {
//                                Request original = chain.request();
//
//                                return chain.proceed(original);
//                            }
//                        }
//                ).build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static Retrofit getRetrofitInstance() {
        if (retrofit3 == null) {
            retrofit3 = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit3;
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
