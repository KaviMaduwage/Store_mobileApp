package com.example.assignment.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.assignment.R;
import com.example.assignment.adapter.CardAdapter;
import com.example.assignment.model.TripDetailForSubscriber;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class ListView extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CardAdapter adapter;
    Toolbar toolbar;
    private ArrayList<TripDetailForSubscriber> tripDetailForSubscribers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        InitializeCardView();

        toolbar = findViewById(R.id.toolbarListView);
        setSupportActionBar(toolbar);
    }

    private void InitializeCardView() {
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tripDetailForSubscribers = new ArrayList<>();

        adapter = new CardAdapter(this, tripDetailForSubscribers);
        recyclerView.setAdapter(adapter);

        createDataForCards();
    }

    private void createDataForCards() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        String dataList = sharedPreferences.getString("tripDetails", "");

        Gson gson = new Gson();
        Type hashMapType = new TypeToken<HashMap<Integer, TripDetailForSubscriber>>() {}.getType();
        HashMap<Integer, TripDetailForSubscriber> hashMapForTripDetails = gson.fromJson(dataList, hashMapType);

        for(int key: hashMapForTripDetails.keySet()){
            TripDetailForSubscriber t = hashMapForTripDetails.get(key);
            tripDetailForSubscribers.add(t);
        }

        adapter.notifyDataSetChanged();

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
        }else if(item.getItemId() == R.id.backOpt){
            back();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void back() {
        Intent intent = new Intent(this, DashBoard.class);
        startActivity(intent);
    }

    private void logout() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}