package com.example.task91p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.task91p.data.AdvertDatabaseHelper;
import com.example.task91p.model.Advert;
import com.example.task91p.util.AdvertUtil;

public class AdvertDetailsActivity extends AppCompatActivity {
    TextView advertType, advertName, advertDesc, date, location;
    Button removeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert_details);

        advertType = findViewById(R.id.advert_type);
        advertName = findViewById(R.id.advert_name);
        advertDesc = findViewById(R.id.advert_desc);
        date = findViewById(R.id.date);
        location = findViewById(R.id.location);
        removeBtn = findViewById(R.id.remove_btn);

        Intent i = getIntent();

        AdvertDatabaseHelper databaseHelper = new AdvertDatabaseHelper(this);
        Advert advert = databaseHelper.getAdvertDetails(i.getIntExtra(AdvertUtil.ADVERT_ID,0));
        advertType.setText(advert.getType());
        advertName.setText(advert.getName());
        advertDesc.setText(advert.getDescription());
        date.setText(advert.getDate());
        location.setText("Lat: "+advert.getLatitude()+", "+"Long: "+advert.getLongitude());

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteAdvert(advert.getAdvertID());
                Intent i = new Intent(AdvertDetailsActivity.this, ShowAdvertsActivity.class);
                startActivity(i);
                finish();
                Toast.makeText(AdvertDetailsActivity.this, "Advert deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}