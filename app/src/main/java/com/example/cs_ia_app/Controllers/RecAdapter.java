package com.example.cs_ia_app.Controllers;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        holder.getTvname().setText(itemList.get(position).getName());
        holder.getTvlocation().setText(itemList.get(position).getLocation());
        holder.getTvID().setText(itemList.get(position).getItemID());
        holder.getTvLink().setText(itemList.get(position).getPurchaseLink());

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
                Picasso.get().load(downloadUrl).into(holder.getImageView());
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
