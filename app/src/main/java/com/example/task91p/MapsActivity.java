package com.example.task91p;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.task91p.data.AdvertDatabaseHelper;
import com.example.task91p.databinding.ActivityMapsBinding;
import com.example.task91p.model.Advert;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
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
        AdvertDatabaseHelper db = new AdvertDatabaseHelper(this);
        ArrayList<Advert> adverts = db.getAllAdverts();
        if (adverts.size() > 0) {
            int i = 0;
            LatLng init_loc = new LatLng(Double.valueOf(adverts.get(i).getLatitude()), Double.valueOf(adverts.get(i).getLongitude()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(init_loc));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(init_loc, 17f));
            while (i < adverts.size()) {
                LatLng loc = new LatLng(Double.valueOf(adverts.get(i).getLatitude()), Double.valueOf(adverts.get(i).getLongitude()));
                mMap.addMarker(new MarkerOptions()
                        .position(loc)
                        .title(adverts.get(i).getType() + " " + adverts.get(i).getName()));
                i++;
            }
        }
    }
}