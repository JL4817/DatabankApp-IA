package com.example.cs_ia_app.Controllers;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs_ia_app.Models.Item;
import com.example.cs_ia_app.R;

import java.util.ArrayList;

public class RecHolder extends RecyclerView.ViewHolder {

    private TextView tvname;
    private TextView tvlocation;
    private TextView tvID;
    private TextView tvLink;
    private ImageView imageView;


    public RecHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.ivItemShow);
        tvname = itemView.findViewById(R.id.etItemNameDisplay);
        tvlocation = itemView.findViewById(R.id.etItemLocationDisplay);
        tvID = itemView.findViewById(R.id.etItemIDDisplay);
        tvLink = itemView.findViewById(R.id.etItemLinkDisplay);

    }

    public interface ItemClickListener {
        void onItemClick(ArrayList<Item> details, int position);
    }

    public TextView getTvname() {
        return tvname;
    }

    public void setTvname(TextView tvname) {
        this.tvname = tvname;
    }

    public TextView getTvlocation() {
        return tvlocation;
    }

    public void setTvlocation(TextView tvlocation) {
        this.tvlocation = tvlocation;
    }

    public TextView getTvID() {
        return tvID;
    }

    public void setTvID(TextView tvID) {
        this.tvID = tvID;
    }

    public TextView getTvLink() {
        return tvLink;
    }

    public void setTvLink(TextView tvLink) {
        this.tvLink = tvLink;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }


}
