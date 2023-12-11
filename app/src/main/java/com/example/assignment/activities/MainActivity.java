package com.example.assignment.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment.R;
import com.example.assignment.db.DatabaseHelper;
import com.example.assignment.model.DriverLogins;
import com.example.assignment.retrofit.NewsStoreAPI;
import com.example.assignment.retrofit.RetrofitService;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText driverId;
    EditText password;
    Button login;
    RetrofitService retrofitService;
    NewsStoreAPI newsStoreAPI;

    DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        driverId = (EditText) findViewById(R.id.txtUserId);
        password = (EditText) findViewById(R.id.txtPassword);
        login = (Button) findViewById(R.id.btnLogin);
        myDB = new DatabaseHelper(this);

        retrofitService = new RetrofitService();
        newsStoreAPI = retrofitService.getRetrofit().create(NewsStoreAPI.class);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginSuccess();
            }
        });


    }

    private void loginSuccess() {
        Call<DriverLogins> call = newsStoreAPI.getDriverLogin(driverId.getText().toString());
        call.enqueue(new Callback<DriverLogins>() {
            @Override
            public void onResponse(Call<DriverLogins> call, Response<DriverLogins> response) {
                if (response.isSuccessful()) {
                    DriverLogins login = response.body();
                    if(login == null || login.getDriverId() == null || !login.getPassword().equals(password.getText().toString())){
                        showMessage("Error","Invalid user id or password.");

                    }else{
                        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userId",login.getDriverId());
                        editor.putString("userName", login.getDriverUserName());
                        editor.apply();

                        Intent intent = new Intent(MainActivity.this, DashBoard.class);
                        startActivity(intent);
                    }


                } else {
                    // Handle the unsuccessful response
                    Toast.makeText(MainActivity.this,"success fail",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DriverLogins> call, Throwable t) {
                Toast.makeText(MainActivity.this,"save failed",Toast.LENGTH_SHORT).show();
                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE,"Error occured",t);
            }
        });
//        Cursor result = myDB.getUserData(userId.getText().toString());
//        if(result.getCount() == 0){
//            return false;
//        }else{
//            if(result != null && result.moveToFirst()){
//
//                if(result.getString(1).equals(password.getText().toString())){
//                    return true;
//                }else {
//                    return false;
//                }
//            }else{
//                return false;
//            }
//
//        }
    }
    public void showMessage(String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}