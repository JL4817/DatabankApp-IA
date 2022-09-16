package com.example.cs_ia_app.Controllers;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs_ia_app.Models.Item;
import com.example.cs_ia_app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



public class RecAdapter extends RecyclerView.Adapter<RecHolder> {

    private FirebaseStorage storage;


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

        String imageUri = item.getItemImage();

        // Points to the root reference
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference dateRef = storageRef.child("images/" + imageUri);
        dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
        {
            @Override
            public void onSuccess(Uri downloadUrl)
            {
                Picasso.get().load(downloadUrl).into(holder.imageView);
            }
        });


        holder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(itemList, position);
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }




}
