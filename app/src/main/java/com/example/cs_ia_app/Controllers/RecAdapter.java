/**

 This is the adapter class for the RecyclerView in the ItemInfoActivity. It sets the data for the
 RecHolder and binds the RecHolder to the data.
 */

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

    private ArrayList<Item> itemList;
    private RecHolder.ItemClickListener mItemListener;

    /**
     * Constructor for the RecAdapter class.
     *
     * @param itemList the ArrayList of items to be displayed in the RecyclerView
     * @param itemClickListener the click listener for the items in the RecyclerView
     */
    public RecAdapter(ArrayList itemList, RecHolder.ItemClickListener itemClickListener) {
        this.itemList = itemList;
        this.mItemListener = itemClickListener;
    }

    /**
     * Called when RecyclerView needs a new RecHolder of the given type to represent an item.
     *
     * @param parent the ViewGroup into which the new View will be added
     * @param viewType the type of the new view
     * @return a new RecHolder that holds a View of the given view type
     */
    @NonNull
    @Override
    public RecHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_row_for_recyclerview, parent, false);

        RecHolder holder = new RecHolder(myView);

        return holder;
    }


    /**
     * Called by RecyclerView to display the data at the specified position. This method updates
     * the contents of the RecHolder to reflect the item at the given position.
     *
     * @param holder the RecHolder which should be updated to represent the contents of the item at
     *               the given position in the data set
     * @param position the position of the item within the adapter's data set
     */
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
        dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri downloadUrl) {
                Picasso.get().load(downloadUrl).into(holder.getImageView());
            }
        });


        holder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(itemList, position);
        });

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return the total number of items in the data set held by the adapter
     */
    @Override
    public int getItemCount() {

        return itemList.size();

    }


}
