package com.example.cs_ia_app.Controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs_ia_app.Models.Item;
import com.example.cs_ia_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RecAdapter extends RecyclerView.Adapter<RecHolder> {

    ArrayList<Item> itemList;
    private RecHolder.ItemClickListener mItemListener;

    public RecAdapter(ArrayList itemList, RecHolder.ItemClickListener itemClickListener){
        this.itemList = itemList;
        this.mItemListener = itemClickListener;
    }




    @NonNull
    @Override
    public RecHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_row_for_recyclerview, parent, false);

        RecHolder holder = new RecHolder(myView);

        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull RecHolder holder, final int position) {

        holder.tvname.setText(itemList.get(position).getName());
        holder.tvlocation.setText(itemList.get(position).getLocation());
        holder.tvID.setText(itemList.get(position).getItemID());
        holder.tvLink.setText(itemList.get(position).getPurchaseLink());

        Item item = itemList.get(position);

        String imageUri = null;
        imageUri = item.getItemImage();
        Picasso.get().load(imageUri).into(holder.imageView);

        holder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(itemList, position);
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }




}
