package com.example.nihar.teamvelociracers;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button vr_connect , about_us , dealer_locator , knowledge_center , contact_us , roadside_assistence , vr_fit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        about_us = (Button)findViewById(R.id.about_us);
        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , AboutUs.class);
                startActivity(intent);
            }
        });
            //Use of GOOGLE API
        dealer_locator = (Button)findViewById(R.id.dealer_locator);
        dealer_locator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , DealerLocations.class);
                startActivity(intent);
            }
        });

        knowledge_center = (Button)findViewById(R.id.knowledeg_center);
        knowledge_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , PDF.class);
                startActivity(intent);
            }
        });
        contact_us = (Button)findViewById(R.id.vr_connect);
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , CurrentWeather.class);
                startActivity(intent);
            }
        });
            //JSON BACKEND RESPONSE
        roadside_assistence = (Button)findViewById(R.id.roadside_assistence);
        roadside_assistence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , ContactUs.class);
                startActivity(intent);
            }
        });

    }

}

//About US
//2)VR Track Eliminate
//Roackside Assistance(Calling feature)
//3)VR Fit    Eliminate
//Knowledge Centre
//4)Contact Us Eliminate  ==>About Us ==>This Year Brochure