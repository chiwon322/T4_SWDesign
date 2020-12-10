package com.team4.finding_sw_developers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPref = getSharedPreferences("status", MODE_PRIVATE);
        try {
            Thread.sleep(2000); //대기 초 설정
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        } catch (Exception e) {
            Log.e("Error", "SplashActivity ERROR", e);
        }
    }

}
