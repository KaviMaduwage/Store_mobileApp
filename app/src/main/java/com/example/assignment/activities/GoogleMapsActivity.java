package com.example.assignment.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.assignment.R;
import com.example.assignment.model.TripDetailForSubscriber;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GoogleMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private Toolbar toolbar;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Geocoder geocoder;
    private List<TripDetailForSubscriber> tripDetailForSubscribers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);

        toolbar = findViewById(R.id.mapViewToolbar);
        setSupportActionBar(toolbar);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Check for location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission granted
            mMap.setMyLocationEnabled(true);
            //getCurrentLocation();
            showAddressesOnMap();
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Location location = task.getResult();
                            LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(currentLatLng).title("Current Location"));
                            mMap.animateCamera(CameraUpdateFactory.newLatLng(currentLatLng));
                        } else {
                            Toast.makeText(GoogleMapsActivity.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission granted
                //getCurrentLocation();
                showAddressesOnMap();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showAddressesOnMap() {
        List<String> addresses = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        String dataList = sharedPreferences.getString("tripDetails", "");

        Gson gson = new Gson();
        Type hashMapType = new TypeToken<HashMap<Integer, TripDetailForSubscriber>>() {}.getType();
        HashMap<Integer, TripDetailForSubscriber> hashMapForTripDetails = gson.fromJson(dataList, hashMapType);

        for(int key: hashMapForTripDetails.keySet()){
            TripDetailForSubscriber t = hashMapForTripDetails.get(key);
            tripDetailForSubscribers.add(t);
            addresses.add(t.getSubscriberAddress());
        }

        //addresses.add("No.448,Galle Road,Patabandimulla,Ambalangoda,Sri Lanka");

        for (String address : addresses) {
            try {
                List<Address> addressList = geocoder.getFromLocationName(address, 1);
                if (addressList != null && addressList.size() > 0) {
                    Address targetAddress = addressList.get(0);
                    double latitude = targetAddress.getLatitude();
                    double longitude = targetAddress.getLongitude();
                    LatLng latLng = new LatLng(latitude, longitude);

                    // Add marker to the map
                    mMap.addMarker(new MarkerOptions().position(latLng).title(address));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                } else {
                    Toast.makeText(this, "Address not found: " + address, Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Geocoding failed", Toast.LENGTH_SHORT).show();
            }
        }
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
