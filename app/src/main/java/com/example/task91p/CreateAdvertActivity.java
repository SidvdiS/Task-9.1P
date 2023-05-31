package com.example.task91p;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.task91p.data.AdvertDatabaseHelper;
import com.example.task91p.model.Advert;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateAdvertActivity extends AppCompatActivity {
    EditText name, phone, desc, date;
    RadioGroup type;
    Button save, getCurrentLocBtn;
    RadioButton defaultCheckedButton;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static final int REQUEST_CODE = 100;
    AutocompleteSupportFragment autocompleteFragment;
    LocationManager locationManager;
    String latitude, longitude;

    int LOCATION_REFRESH_TIME = 15000; // 15 seconds to update
    int LOCATION_REFRESH_DISTANCE = 500; // 500 meters to update

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone_number);
        desc = findViewById(R.id.desc);
        date = findViewById(R.id.date);
        type = findViewById(R.id.type);
        save = findViewById(R.id.save_btn);
        getCurrentLocBtn = findViewById(R.id.get_current_loc_btn);
        Places.initialize(getApplicationContext(), BuildConfig.MAPS_API_KEY);

        defaultCheckedButton =  findViewById(type.getCheckedRadioButtonId());
        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.location_autocomplete);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME));
        type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                defaultCheckedButton = findViewById(i);
            }
        });

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                latitude = String.valueOf(place.getLatLng().latitude);
                longitude = String.valueOf(place.getLatLng().longitude);
            }


            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: " + status);
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getCurrentLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Advert advert = new Advert(name.getText().toString(),
                        phone.getText().toString(), desc.getText().toString(),
                        date.getText().toString(),latitude,longitude,
                        defaultCheckedButton.getText().toString());
                if(TextUtils.isEmpty(advert.getName())){
                    showToast("Enter item name");
                    name.setError("Enter item name");
                    return;
                } else if (TextUtils.isEmpty(advert.getPhoneNumber())) {
                    showToast("Enter phone number");
                    phone.setError("Enter phone number");
                    return;
                } else if (TextUtils.isEmpty(advert.getDescription())) {
                    showToast("Enter item description");
                    desc.setError("Enter item description");
                    return;
                } else if(latitude==null && longitude==null){
                    showToast("Enter location");
                    return;
                } else if(TextUtils.isEmpty(advert.getDate())){
                    showToast("Enter date");
                    date.setError("Enter date");
                    return;
                }

                AdvertDatabaseHelper advertDatabaseHelper = new AdvertDatabaseHelper(CreateAdvertActivity.this);
                long insert = advertDatabaseHelper.insertAdvert(advert);
                if(insert>0){
                    showToast("Advert created successfully");
                    finish();
                } else {
                    showToast("Sorry! Cannot create advert. Please try again");
                }
            }
        });
    }

    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(CreateAdvertActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE, mLocationListener);
        } else {
            askPermission();
        }
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            latitude = String.valueOf(location.getLatitude());
            longitude = String.valueOf(location.getLongitude());
            Geocoder geocoder = new Geocoder(CreateAdvertActivity.this);
            ArrayList<Address> address = null;
            try {
               address = (ArrayList<Address>) geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            autocompleteFragment.setText(address.get(0).getAddressLine(0));
        }
    };

    private void askPermission() {
        ActivityCompat.requestPermissions(CreateAdvertActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }else {
                showToast("Please provide the required permission");
            }

        }
    }

    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}