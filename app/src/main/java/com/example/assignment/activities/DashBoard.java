package com.example.assignment.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.R;
import com.example.assignment.activities.MainActivity;
import com.example.assignment.model.DriverLogins;
import com.example.assignment.model.SubscriberDetail;
import com.example.assignment.model.SubscriptionDetail;
import com.example.assignment.model.TripDetail;
import com.example.assignment.model.TripDetailForSubscriber;
import com.example.assignment.retrofit.NewsStoreAPI;
import com.example.assignment.retrofit.RetrofitService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoard extends AppCompatActivity {

    Toolbar toolbar;
    DatePicker datePicker;
    Button loadListButton,loadMapButton;
    String userName,userId,dateSelected;
    RetrofitService retrofitService;
    NewsStoreAPI newsStoreAPI;

    boolean isMap = false;
    boolean isList = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        retrofitService = new RetrofitService();
        newsStoreAPI = retrofitService.getRetrofit().create(NewsStoreAPI.class);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", "");
        userId = sharedPreferences.getString("userId", "");
        String welcomeTxt = "Welcome "+sharedPreferences.getString("userName", "") + ". Let's begin the day !";

        TextView txtUserId = findViewById(R.id.userIdTxt);
        txtUserId.setText(welcomeTxt);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        loadListButton = (Button) findViewById(R.id.btnList);
        loadMapButton = (Button) findViewById(R.id.btnMap);


        loadListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isList = true;
                loadSchedule();
            }
        });

        loadMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMap = true;
                loadSchedule();
            }
        });
    }

    private void loadSchedule() {
        dateSelected = datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth();
        String id = userId;

        Call<DriverLogins> call = newsStoreAPI.getTripDetailsByIdAndDate(id,dateSelected);
        call.enqueue(new Callback<DriverLogins>() {
            @Override
            public void onResponse(Call<DriverLogins> call, Response<DriverLogins> response) {
                if (response.isSuccessful()) {
                    DriverLogins result = response.body();
                    HashMap<Integer, TripDetailForSubscriber> hashMapForTripDetails = new HashMap<>();

                    List<TripDetail> tripDetailList = result.getTripDetails();
                    for(TripDetail tripDetail : tripDetailList){
                        int tripId = tripDetail.getTripId();
                        String tripStatus = tripDetail.getStatus();

                        SubscriberDetail subscriberDetail = tripDetail.getSubscriberDetail();
                        int subscriberId = subscriberDetail.getSubscriberId();
                        if(!hashMapForTripDetails.containsKey(subscriberId)){
                            TripDetailForSubscriber tripDetailForSubscriber = new TripDetailForSubscriber();

                            String subscriberName = subscriberDetail.getSubscriberName();
                            String subscriberAddress = subscriberDetail.getSubscriberAddress();
                            String subscriberPhoneNUmber = subscriberDetail.getSubscriberTel();

                            SubscriptionDetail subscriptionDetail = tripDetail.getSubscriptionDetail();
                            List<SubscriptionDetail> subscriptionList = new ArrayList<>();
                            subscriptionList.add(subscriptionDetail);

                            tripDetailForSubscriber.setSubscriberName(subscriberName);
                            tripDetailForSubscriber.setSubscriberAddress(subscriberAddress);
                            tripDetailForSubscriber.setSubscriberPhoneNUmber(subscriberPhoneNUmber);
                            tripDetailForSubscriber.setSubscriptionDetailList(subscriptionList);

                            hashMapForTripDetails.put(subscriberId,tripDetailForSubscriber);

                        }else{
                            TripDetailForSubscriber tripDetailForSubscriber = hashMapForTripDetails.get(subscriberId);
                            List<SubscriptionDetail> currentSubscriptionList = tripDetailForSubscriber.getSubscriptionDetailList();
                            currentSubscriptionList.add(tripDetail.getSubscriptionDetail());

                            tripDetailForSubscriber.setSubscriptionDetailList(currentSubscriptionList);

                            hashMapForTripDetails.replace(subscriberId,tripDetailForSubscriber);


                        }


                    }
                    Gson gson = new Gson();
                    String hashMapJson = gson.toJson(hashMapForTripDetails);

                    SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("tripDetails",hashMapJson);
                    editor.apply();

                    if(isList){
                        Intent intent = new Intent(DashBoard.this, ListView.class);
                        startActivity(intent);
                    }else if(isMap){
                        Intent intent = new Intent(DashBoard.this, GoogleMapsActivity.class);
                        startActivity(intent);
                    }




                } else {
                    // Handle the unsuccessful response
                    Toast.makeText(DashBoard.this,"success fail",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DriverLogins> call, Throwable t) {
                Toast.makeText(DashBoard.this,"save failed",Toast.LENGTH_SHORT).show();
                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE,"Error occured dashboard",t);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logoutOpt) {
            logout(); // Call the method to navigate to the main activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}