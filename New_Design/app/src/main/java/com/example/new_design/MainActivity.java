package com.example.new_design;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static final String MSG="com.example.new_design.MSG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start_app(View view)
    {
        Intent intent=new Intent(this,Homepage.class);
        //ImageView vehicleimage=findViewById(R.id.button3);
        String message="Pratik Patil";
        intent.putExtra(MSG,message);
        startActivity(intent);
    }

}