package com.example.letsdonate;

import androidx.annotation.NonNull;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;

import android.content.Context;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapForUser extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng user_latlong; // user's location
    MarkerOptions user_marker;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_for_user);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},PackageManager.PERMISSION_GRANTED);
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
        user_latlong = new LatLng(28.7041, 77.1025);
        user_marker = new MarkerOptions().position(user_latlong).title("New delhi");
        mMap.addMarker(user_marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(user_latlong));


        // To Handle the GPS events
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mMap.clear();

                user_latlong = new LatLng(location.getLatitude(), location.getLongitude());
                user_marker = new MarkerOptions().position(user_latlong).title("You");
                mMap.addMarker(user_marker);
               // mMap.moveCamera(CameraUpdateFactory.newLatLng(user_latlong));
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(user_latlong));
            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };


        // To USE GPS
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        try {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);

        } catch (SecurityException ex) {
            ex.printStackTrace();
        }


    }

}
