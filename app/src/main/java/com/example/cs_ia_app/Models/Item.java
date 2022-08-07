package com.example.cs_ia_app.Models;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.Serializable;

public class Item implements Serializable, Parcelable {

    private String itemID;
    private String location;
    private String name;
    private String purchaseLink;
    private String itemImage;
    private String owner;


    public Item(String itemID, String location, String name, String purchaseLink, String itemImage, String owner) {
        this.itemID = itemID;
        this.location = location;
        this.name = name;
        this.purchaseLink = purchaseLink;
        this.itemImage = itemImage;
        this.owner = owner;
    }

    public Item(){
         itemID = "";
         location = "";
         name = "";
         purchaseLink = "";
         itemImage = "";
         owner = "";
    }


    protected Item(Parcel in) {
        itemID = in.readString();
        location = in.readString();
        name = in.readString();
        purchaseLink = in.readString();
        itemImage = in.readString();
        owner = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemID);
        dest.writeString(location);
        dest.writeString(name);
        dest.writeString(purchaseLink);
        dest.writeString(itemImage);
        dest.writeString(owner);
    }


    @Override
    public String toString() {
        return "Item{" +
                "itemID='" + itemID + '\'' +
                ", location='" + location + '\'' +
                ", name='" + name + '\'' +
                ", purchaseLink='" + purchaseLink + '\'' +
                ", itemImage='" + itemImage + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
