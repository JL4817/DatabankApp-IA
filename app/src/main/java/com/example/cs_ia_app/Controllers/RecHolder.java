/**

 This class represents the holder of the RecyclerView, which holds the Views to be displayed in each row of the RecyclerView.
 It also provides getter and setter methods for each of the Views, and an interface for the click listener.
 */
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

    /**
     * The constructor for the RecHolder class.
     * It sets up the Views in the row by finding them in the item view.
     *
     * @param itemView The item view that holds the Views to be displayed in the row.
     */
    public RecHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.ivItemShow);
        tvname = itemView.findViewById(R.id.etItemNameDisplay);
        tvlocation = itemView.findViewById(R.id.etItemLocationDisplay);
        tvID = itemView.findViewById(R.id.etItemIDDisplay);
        tvLink = itemView.findViewById(R.id.etItemLinkDisplay);

    }

    /**
     * An interface for the click listener of the RecyclerView row.
     */
    public interface ItemClickListener {
        void onItemClick(ArrayList<Item> details, int position);
    }

    /**
     * Getter method for the TextView that displays the name of the item in the row.
     *
     * @return The TextView that displays the name of the item in the row.
     */
    public TextView getTvname() {
        return tvname;
    }

    /**
     * Setter method for the TextView that displays the name of the item in the row.
     *
     * @param tvname The TextView that displays the name of the item in the row.
     */
    public void setTvname(TextView tvname) {
        this.tvname = tvname;
    }

    /**
     * Getter method for the TextView that displays the location of the item in the row.
     *
     * @return The TextView that displays the location of the item in the row.
     */
    public TextView getTvlocation() {
        return tvlocation;
    }

    /**
     * Setter method for the TextView that displays the location of the item in the row.
     *
     * @param tvlocation The TextView that displays the location of the item in the row.
     */
    public void setTvlocation(TextView tvlocation) {
        this.tvlocation = tvlocation;
    }

    /**
     * Getter method for the TextView that displays the ID of the item in the row.
     *
     * @return The TextView that displays the ID of the item in the row.
     */
    public TextView getTvID() {
        return tvID;
    }

    /**
     * Setter method for the TextView that displays the ID of the item in the row.
     *
     * @param tvID The TextView that displays the ID of the item in the row.
     */
    public void setTvID(TextView tvID) {
        this.tvID = tvID;
    }

    /**
     * Getter method for the TextView that displays the purchase link of the item in the row.
     *
     * @return The TextView that displays the purchase link of the item in the row.
     */
    public TextView getTvLink() {
        return tvLink;
    }

    /**
     * Setter method for the TextView that displays the purchase link of the item in the row.
     *
     * @param tvLink The TextView that displays the purchase link of the item in the row.
     */
    public void setTvLink(TextView tvLink) {
        this.tvLink = tvLink;
    }

    /**
     * Getter method for the TextView that displays the image of the item in the row.
     *
     * @return The TextView that displays the image of the item in the row.
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Setter method for the TextView that displays the image of the item in the row.
     *
     * @param imageView The TextView that displays the image of the item in the row.
     */
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }


}
