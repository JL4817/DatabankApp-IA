package com.example.cs_ia_app.Models;

import android.media.Image;
import android.widget.ImageView;

public class Item {

    //private String location;
    private String itemID;
    //private String purchaseLink;

    private ImageView itemImage;



    public Item(String itemID, ImageView itemImage) {
        this.itemID = itemID;
        this.itemImage = itemImage;
    }


    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public ImageView getItemImage() {
        return itemImage;
    }

    public void setItemImage(ImageView itemImage) {
        this.itemImage = itemImage;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemID='" + itemID + '\'' +
                ", itemImage=" + itemImage +
                '}';
    }
}
