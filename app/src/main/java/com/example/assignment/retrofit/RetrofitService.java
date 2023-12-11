package com.example.assignment.retrofit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitService {
    private Retrofit retrofit;
    public RetrofitService() {
        initializeRetrofit();
    }
    private void initializeRetrofit() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        // add timeout
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS);
        OkHttpClient httpClient = httpClientBuilder.build();

        retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.100:8080")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    public Retrofit getRetrofit(){
        return retrofit;
    }
}
