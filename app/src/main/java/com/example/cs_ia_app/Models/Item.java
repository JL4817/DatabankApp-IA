package com.example.cs_ia_app.Models;

import android.media.Image;
import android.widget.EditText;
import android.widget.ImageView;

public class Item {

    private String itemID;

    private String location;
    private String name;
    private String purchaseLink;

    private String itemImage;


    public Item(String itemID, String location, String name, String purchaseLink, String itemImage) {
        this.itemID = itemID;
        this.location = location;
        this.name = name;
        this.purchaseLink = purchaseLink;
        this.itemImage = itemImage;
    }


    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurchaseLink() {
        return purchaseLink;
    }

    public void setPurchaseLink(String purchaseLink) {
        this.purchaseLink = purchaseLink;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemID='" + itemID + '\'' +
                ", location='" + location + '\'' +
                ", name='" + name + '\'' +
                ", purchaseLink='" + purchaseLink + '\'' +
                ", itemImage=" + itemImage +
                '}';
    }

}
