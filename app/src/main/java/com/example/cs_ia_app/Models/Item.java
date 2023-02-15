/**

 Represents an item in the system.
 An item has a unique ID, a location, a name, a purchase link, an image, and an owner.
 This class implements the Parcelable interface to allow for passing instances of this class between activities.
 */

package com.example.cs_ia_app.Models;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.Serializable;

public class Item implements Parcelable {

    private String itemID;
    private String location;
    private String name;
    private String purchaseLink;
    private String itemImage;
    private String owner;

    /**
     * Default constructor for an item. Initializes all fields to "unknown".
     */
    public Item() {
        itemID = "unknown";
        location = "unknown";
        name = "unknown";
        purchaseLink = "unknown";
        itemImage = "unknown";
        owner = "unknown";
    }

    /**
     * Constructor for an item with all fields specified.
     *
     * @param itemID       The unique ID of the item.
     * @param location     The location of the item.
     * @param name         The name of the item.
     * @param purchaseLink The purchase link for the item.
     * @param itemImage    The image of the item.
     * @param owner        The owner of the item.
     */
    public Item(String itemID, String location, String name, String purchaseLink, String itemImage, String owner) {
        this.itemID = itemID;
        this.location = location;
        this.name = name;
        this.purchaseLink = purchaseLink;
        this.itemImage = itemImage;
        this.owner = owner;
    }


    /**
     * Describes the contents of the Parcelable instance. This is always set to 0.
     *
     * @return 0
     */
    @Override
    public int describeContents() {
        return 0;
    }


    /**
     * Writes the values of the Parcelable instance to the destination parcel.
     *
     * @param dest  The parcel to write to.
     * @param flags Flags to be set.
     */

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemID);
        dest.writeString(location);
        dest.writeString(name);
        dest.writeString(purchaseLink);
        dest.writeString(itemImage);
        dest.writeString(owner);
    }


    /**
     * Creator object for the Item class. Used to create new instances of the class from a parcel.
     */
    public static final Creator<Item> CREATOR = new Creator<Item>() {

        /**
         * Creates a new instance of the Item class from the given parcel.
         *
         * @param in The parcel to create an instance from.
         * @return A new instance of the Item class.
         */
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        /**
         * Creates an array of Item objects of the given size.
         *
         * @param size The size of the array.
         * @return An array of Item objects.
         */
        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    /**
     * Constructor that creates a new instance of the Item class from the given parcel.
     *
     * @param in The parcel to create an instance from.
     */
    protected Item(Parcel in) {
        itemID = in.readString();
        location = in.readString();
        name = in.readString();
        purchaseLink = in.readString();
        itemImage = in.readString();
        owner = in.readString();
    }

    /**

     Returns the ID of the item.
     @return The ID of the item.
     */
    public String getItemID() {
        return itemID;
    }

    /**

     Sets the ID of the item.
     @param itemID The ID of the item to be set.
     */
    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    /**

     Returns the image of the item.
     @return The image of the item.
     */
    public String getItemImage() {
        return itemImage;
    }

    /**

     Sets the image of the item.
     @param itemImage The image of the item to be set.
     */
    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    /**

     Returns the location of the item.
     @return The location of the item.
     */
    public String getLocation() {
        return location;
    }

    /**

     Sets the location of the item.
     @param location The location of the item to be set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**

     Returns the name of the item.
     @return The name of the item.
     */
    public String getName() {
        return name;
    }

    /**

     Sets the name of the item.
     @param name The name of the item to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**

     Returns the purchase link of the item.
     @return The purchase link of the item.
     */
    public String getPurchaseLink() {
        return purchaseLink;
    }

    /**

     Sets the purchase link of the item.
     @param purchaseLink The purchase link of the item to be set.
     */
    public void setPurchaseLink(String purchaseLink) {
        this.purchaseLink = purchaseLink;
    }

    /**

     Returns the owner of the item.
     @return The owner of the item.
     */
    public String getOwner() {
        return owner;
    }

    /**

     Sets the owner of the item.
     @param owner The owner of the item to be set.
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Returns a string representation of the Item object.
     *
     * @return a string representation of the Item object.
     */
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
