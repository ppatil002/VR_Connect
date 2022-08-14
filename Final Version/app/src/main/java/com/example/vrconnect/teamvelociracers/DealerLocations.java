package com.example.vrconnect.teamvelociracers;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.vrconnect.teamvelociracers.databinding.ActivityDealerLocationsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DealerLocations extends FragmentActivity implements OnMapReadyCallback {
    private static final LatLng Wakad = new LatLng(18.609320,73.769690);
    private static final LatLng Hadapsar = new LatLng(18.507070,73.959360);
    private static final LatLng ShivajiNagar = new LatLng(18.535960,73.833470);
    private static final LatLng Nigdi = new LatLng(18.647630,73.767600);
    private static final LatLng Kothrud = new LatLng(18.5074,73.8077);
    private static final LatLng Aundh = new LatLng(18.565130,73.805490);
    private static final LatLng VimanNagar = new LatLng(18.572390,73.906550);


    private Marker mVimanNagar;
    private Marker mAundh;
    private Marker mKothrud;
    private Marker mNigdi;
    private Marker mHadapsar;
    private Marker mShivajiNagar;
    private Marker mWakad;

    private GoogleMap mMap;
    private ActivityDealerLocationsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDealerLocationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mHadapsar = mMap.addMarker((new MarkerOptions()
                .position(Hadapsar)
                .title("Hadapsar")));
        mHadapsar.setTag(0);

        mShivajiNagar = mMap.addMarker((new MarkerOptions()
                .position(ShivajiNagar)
                .title("Shivaji Nagar")));
        mShivajiNagar.setTag(0);

        mWakad = mMap.addMarker((new MarkerOptions()
                .position(Wakad)
                .title("Wakad")));
        mWakad.setTag(0);

        mNigdi = mMap.addMarker(new MarkerOptions()
                .position(Nigdi)
                .title("Nigdi"));
        mNigdi.setTag(0);

        mKothrud = mMap.addMarker(new MarkerOptions()
                .position(Kothrud)
                .title("Kothrud"));
        mKothrud.setTag(0);

        mAundh = mMap.addMarker(new MarkerOptions()
                .position(Aundh)
                .title("Aundh"));
        mAundh.setTag(0);

        mVimanNagar = mMap.addMarker(new MarkerOptions()
                .position(VimanNagar)
                .title("Viman Nagar"));
        mVimanNagar.setTag(0);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ShivajiNagar , 11));

        // Set a listener for marker click.


    }
}