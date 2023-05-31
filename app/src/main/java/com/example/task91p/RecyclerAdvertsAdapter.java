package com.example.task91p;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task91p.model.Advert;
import com.example.task91p.util.AdvertUtil;

import java.util.ArrayList;

public class RecyclerAdvertsAdapter extends RecyclerView.Adapter<RecyclerAdvertsAdapter.CustomViewHolder> {

    ArrayList<Advert> adverts;

    Activity activity;
    public RecyclerAdvertsAdapter(Activity activity, ArrayList<Advert> adverts){
        this.adverts = adverts;
        this.activity = activity;
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.advert_row,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.titleView.setText(adverts.get(position).getType() + " " + adverts.get(position).getName());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, AdvertDetailsActivity.class);
                i.putExtra(AdvertUtil.ADVERT_ID, adverts.get(position).getAdvertID());
                activity.startActivity(i);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return adverts.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        CardView rootView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.item_title);
            rootView = itemView.findViewById(R.id.root_advert);
        }
    }
}
