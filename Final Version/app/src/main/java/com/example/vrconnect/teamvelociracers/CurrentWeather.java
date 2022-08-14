package com.example.vrconnect.teamvelociracers;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.location.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;;


public class CurrentWeather extends AppCompatActivity {

    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "22d63b6ed5298f240ca16ef0c866036b";

    private final String urlw = "https://api.weatherbit.io/v2.0/";
    private final String appidw = "f72541d3d5c44439a4f9e3144ae2533b";

    private TextView timeZone,cityName,sunRise,sunSet,solarRadiation,temperature,precipitation,AQI,visibility,description,observedTime,degreeSymbol,celciusSymbol;
    private ProgressBar progressBar;

    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
    Location location;
    private LocationRequest locationRequest;
    public String latitude;
    public String longitude;
    public static final DecimalFormat decimalformat=new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);


        timeZone=findViewById(R.id.timeZone);
        cityName=findViewById(R.id.cityName);
        sunRise=findViewById(R.id.sunRise);
        sunSet=findViewById(R.id.sunSet);
        solarRadiation=findViewById(R.id.solar_rad);
        temperature=findViewById(R.id.temperature);
        precipitation=findViewById(R.id.precipitation);
        AQI=findViewById(R.id.AQI);
        visibility=findViewById(R.id.visibility);
        description=findViewById(R.id.description);
        observedTime=findViewById(R.id.observedTime);
        degreeSymbol=findViewById(R.id.degreeSymbol);
        celciusSymbol=findViewById(R.id.celciusSymbol);
        progressBar=findViewById(R.id.progressBar);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);


        getCurrentLocation();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (isGPSEnabled()) {
                    getCurrentLocation();
                }else {
                    turnOnGPS();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                getCurrentLocation();
            }
        }
    }

    private void getCurrentLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(CurrentWeather.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(CurrentWeather.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(CurrentWeather.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() >0){

                                        int index = locationResult.getLocations().size() - 1;
                                        double lat = locationResult.getLocations().get(index).getLatitude();
                                        latitude=decimalformat.format(lat);
                                        double lon = locationResult.getLocations().get(index).getLongitude();
                                        longitude=decimalformat.format(lon);
                                        getWeatherDetails();
                                    }
                                }
                            }, Looper.getMainLooper());
                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }


    private void turnOnGPS() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(CurrentWeather.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(CurrentWeather.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }


    public void getWeatherDetails() {
        //OpenWeathermap.org
        //Enter City Name(Pune)
        //All values are shown in the text box
//        String tempUrl ="";
//        String city = etCity.getText().toString().trim();
//        String country = etCountry.getText().toString().trim();
//        if(city.equals("")){
//            tvResult.setText("City field can not be empty!");
//        }else{
//            if(!country.equals("")){
//                tempUrl = url + "?q=" + city + "," + country + "&appid=" + appid;
//            }else{
//                tempUrl = url + "?q=" + city + "&appid=" + appid;
//            }
//            if(!country.equals("")){
//                tempUrl = url + "?q=" + city + "," + country + "&appid=" + appid;
//          }
//            else{
//                tempUrl = url + "?q=" + city + "&appid=" + appid;
//                Toast.makeText(this, "G", Toast.LENGTH_SHORT).show();
//            }
//            String finalTempUrl = tempUrl;
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    String output = "";
//                    try {
//                        JSONObject jsonResponse = new JSONObject(response);
//                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
//                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
//                        String description = jsonObjectWeather.getString("description");
//                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
//                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
//                        double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
//                        float pressure = jsonObjectMain.getInt("pressure");
//                        int humidity = jsonObjectMain.getInt("humidity");
//                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
//                        String wind = jsonObjectWind.getString("speed");
//                        JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
//                        String clouds = jsonObjectClouds.getString("all");
//                        JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
//                        String countryName = jsonObjectSys.getString("country");
//                        String cityName = jsonResponse.getString("name");
////                        tvResult.setTextColor(Color.rgb(68,134,199));
////                        output += "Current weather of " + cityName + " (" + countryName + ")"
////                                + "\n Temp: " + df.format(temp) + " °C"
////                                + "\n Feels Like: " + df.format(feelsLike) + " °C"
////                                + "\n Humidity: " + humidity + "%"
////                                + "\n Description: " + description
////                                + "\n Wind Speed: " + wind + "m/s (meters per second)"
////                                + "\n Cloudiness: " + clouds + "%"
////                                + "\n Pressure: " + pressure + " hPa";
////                      tvResult.setText(output);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener(){
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
//                    String strerror=error.toString();
//                }
//            });
//            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//            requestQueue.add(stringRequest);




        //weatherbit.io
        //All Values are updated in Logcat Debug section

        String tempUrl = "";

        RequestQueue requestQueue;
        requestQueue=Volley.newRequestQueue(this);

        tempUrl = urlw + "current?lat=" + latitude + "&lon=" + longitude + "&key=" + appidw;

        String finalTempUrl = tempUrl;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, tempUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            JSONObject obj = jsonArray.getJSONObject(0);
//                            Log.d("myApp", "Last Observed Time:"
//                                    + obj.getString("ob_time"));
//                            Log.d("myApp", "TimeZone:"
//                                    + obj.getString("timezone"));
//                            Log.d("myApp", "City Name:"
//                                    + obj.getString("city_name"));
//                            Log.d("myApp", "Sunrise Time:"  //UTC TIME
//                                    + obj.getString("sunrise"));
//                            Log.d("myApp", "Sunset Time:"
//                                    + obj.getString("sunset"));
//                            Log.d("myApp", "Solar Radiation"
//                                    + obj.getString("solar_rad"));
//                            Log.d("myApp", "Temperature"
//                                    + obj.getString("temp"));
//                            Log.d("myApp", "Precipitation:"
//                                    + obj.getString("precip"));
//                            Log.d("myApp", "AQI:"
//                                    + obj.getString("aqi"));
//                            Log.d("myApp", "Visibility:"
//                                    + obj.getString("vis"));
                            JSONObject weather = new JSONObject();
                            weather = obj.getJSONObject("weather");
//                            Log.d("myApp", weather.get("description").toString());
                            String observetime=obj.getString("ob_time");
                            observedTime.setText(addDate(observetime));
                            timeZone.setText(obj.getString("timezone"));
                            cityName.setText(obj.getString("city_name"));
                            String sunrisetime=obj.getString("sunrise");
                            sunRise.setText(addTime(sunrisetime));
                            String sunsettime=obj.getString("sunset");
                            sunSet.setText(addTime(sunsettime));
                            solarRadiation.setText(obj.getString("solar_rad"));
                            temperature.setText(obj.getString("temp"));
                            degreeSymbol.setVisibility(TextView.VISIBLE);
                            celciusSymbol.setVisibility(TextView.VISIBLE);
                            precipitation.setText(obj.getString("precip"));
                            AQI.setText(obj.getString("aqi"));
                            visibility.setText(obj.getString("vis"));
                            description.setText(weather.get("description").toString());
                            progressBar.setVisibility(View.INVISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("myApp", finalTempUrl);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(CurrentWeather.this, "Error fetching Data", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    public String addTime(String time)
    {
        int hour= Integer.parseInt(time.substring(0, 2));
        int minutes= Integer.parseInt(time.substring(3));
        minutes+=30;
        if(minutes>=60)
        {
            minutes-=60;
            hour++;
        }
        hour+=5;
        if(hour>=24)
        {
            hour-=24;
        }
        String hr,min;
        if(hour<10)
            hr="0" + String.valueOf(hour);
        else
            hr= String.valueOf(hour);
        if(minutes<10)
            min="0" + String.valueOf(minutes);
        else
            min= String.valueOf(minutes);
        String output= hr + ":" + min + " IST";
//        Log.d("myApp", output);
        return output;
    }

    public String addDate(String date)
    {
        String output=date.substring(0, 11) + addTime(date.substring(11));
        return output;
    }

}