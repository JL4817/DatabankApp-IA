package com.example.cs_ia_app.Controllers;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs_ia_app.Models.Item;
import com.example.cs_ia_app.R;

import java.util.ArrayList;

public class RecHolder extends RecyclerView.ViewHolder{

    protected TextView tvname;
    protected TextView tvlocation;
    protected TextView tvID;
    protected TextView tvLink;

    ImageView imageView;



    public RecHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.ivItemShow);
        tvname = itemView.findViewById(R.id.etItemNameDisplay);
        tvlocation = itemView.findViewById(R.id.etItemLocationDisplay);
        tvID = itemView.findViewById(R.id.etItemIDDisplay);
        tvLink = itemView.findViewById(R.id.etItemLinkDisplay);

    }

    public interface ItemClickListener{
        void onItemClick(ArrayList<Item> details, int position);
    }


}
