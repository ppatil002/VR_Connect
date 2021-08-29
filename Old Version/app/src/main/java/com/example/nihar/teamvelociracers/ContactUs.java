package com.example.nihar.teamvelociracers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.net.Uri.parse;

public class ContactUs extends AppCompatActivity {
    private Button button , button2 ,button1 ,  visit_website;
    private EditText editText1;
    private static final int REQUEST_CALL =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        if(ContextCompat.checkSelfPermission(ContactUs.this , Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(ContactUs.this , Manifest.permission.SEND_SMS)) {
                ActivityCompat.requestPermissions(ContactUs.this , new String[] {Manifest.permission.SEND_SMS} , 1);

            }else {
                ActivityCompat.requestPermissions(ContactUs.this , new String[] {Manifest.permission.SEND_SMS} , 1);
            }
        }else {

        }
        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        editText1 = (EditText)findViewById(R.id.editText2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "8421140135";
                String sms = editText1.getText().toString();
                try{
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, sms, null, null);
                    Toast.makeText(ContactUs.this, "Sent", Toast.LENGTH_SHORT).show();
                }catch (Exception e) {
                    Toast.makeText(ContactUs.this , "Failed" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall1();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

        visit_website = (Button)findViewById(R.id.visit_website);
        visit_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browseIntent = new Intent(Intent.ACTION_VIEW , Uri.parse("https://www.coep.org.in/velociracers/"));
                startActivity(browseIntent);
            }
        });

    }

    private void makePhoneCall() {
        String number = "8421140135";
        if(ContextCompat.checkSelfPermission(ContactUs.this, Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ContactUs.this , new String[] {Manifest.permission.CALL_PHONE} , 2);
        }else{
            String dial = "tel:" + number;

            startActivity(new Intent(Intent.ACTION_CALL , Uri.parse(dial)));
        }
    }
    private void makePhoneCall1() {
        String number = "8806295466";
        if(ContextCompat.checkSelfPermission(ContactUs.this, Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ContactUs.this , new String[] {Manifest.permission.CALL_PHONE} , 3);
        }else{
            String dial = "tel:" + number;

            startActivity(new Intent(Intent.ACTION_CALL , Uri.parse(dial)));
        }
    }


    public void onRequestPermissionResult(int requestCode , String[] permissions , int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if(grantResults.length >0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(ContextCompat.checkSelfPermission(ContactUs.this , Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this , " Permission Grnated " , Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this , " Permission Not Granted " , Toast.LENGTH_SHORT).show();
                }
                return;

            }
            case 2: {
                if(grantResults.length >0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makePhoneCall();
                }else {
                    Toast.makeText(this , " Permission Not Granted " , Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 3: {
                if(grantResults.length >0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makePhoneCall1();
                }else {
                    Toast.makeText(this , " Permission Not Granted " , Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}