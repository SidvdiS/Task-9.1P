package com.example.task91p;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.task91p.data.AdvertDatabaseHelper;
import com.example.task91p.model.Advert;

import java.util.ArrayList;

public class ShowAdvertsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_adverts);
        recyclerView = findViewById(R.id.recycler_view);

        AdvertDatabaseHelper advertDatabaseHelper = new AdvertDatabaseHelper(this);
        ArrayList<Advert> adverts = advertDatabaseHelper.getAllAdverts();
        RecyclerAdvertsAdapter adapter = new RecyclerAdvertsAdapter(ShowAdvertsActivity.this, adverts);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}