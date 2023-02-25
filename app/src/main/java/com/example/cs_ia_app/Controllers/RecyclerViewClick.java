/**

 The RecyclerViewClick class is responsible for handling the click event of an item in the recycler view.
 It provides functionality to update the item's name, location, purchase link, and image.
 The user can also take a new image for the item.
 The updated item data is stored in Firebase Firestore and Firebase Storage.
 */

package com.example.cs_ia_app.Controllers;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs_ia_app.Models.Item;
import com.example.cs_ia_app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;


public class RecyclerViewClick extends AppCompatActivity implements View.OnClickListener {

    //Firebase Firestore, Firebase Storage, and Firebase Authentication instances.
    private FirebaseFirestore firestore;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    //Views for displaying the item information and for taking a new picture of the item.
    private ImageView imageView;
    private TextView tvlocation, tvname, tvitemLink;
    private String location, name, link;

    private TextView tvName2, tvLink2, tvLocation2;
    private Button takePicture;
    private ImageView tvImageView3;

    //ArrayList for storing the items and position of the selected item.
    private ArrayList<Item> itemList;
    private int position;
    private Item selected;


    /**
     * Method called when the activity is first created.
     * Initializes Firebase Firestore, Firebase Storage, and the views.
     * Sets the information of the selected item in the views.
     * @param savedInstanceState The saved instance state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        //Initialize Firebase Firestore, Firebase Storage, and the views.
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        firestore = FirebaseFirestore.getInstance();

        //Get the selected item's information and set it in the views.
        if (getIntent().hasExtra("itemList") && getIntent().hasExtra("itemPos")) {

            itemList = (ArrayList<Item>) getIntent().getSerializableExtra("itemList");

            position = (int) getIntent().getSerializableExtra("itemPos");
            selected = itemList.get(position);

            tvlocation = findViewById(R.id.lvName2);
            tvname = findViewById(R.id.lvLocation2);
            tvitemLink = findViewById(R.id.lvLink2);
            imageView = findViewById(R.id.lvImageView2);
            takePicture = findViewById(R.id.takePictureBTN);

            tvName2 = findViewById(R.id.lvName3);
            tvLocation2 = findViewById(R.id.lvLocation3);
            tvLink2 = findViewById(R.id.lvLink3);
            tvImageView3 = findViewById(R.id.lvImageView3);

            tvName2.setHint("Enter New Name");
            tvLocation2.setHint("Enter New Location");
            tvLink2.setHint("Enter New Link");

            location = selected.getLocation();
            name = selected.getName();
            link = selected.getPurchaseLink();

            tvlocation.setText(location);
            tvname.setText(name);
            tvitemLink.setText(link);

            String imageUri = selected.getItemImage();
            // Points to the root reference
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference dateRef = storageRef.child("images/" + imageUri);
            dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri downloadUrl) {
                    Picasso.get().load(downloadUrl).into(imageView);
                }
            });
        }
    }

    /**

     This method checks if the app has permission to use the camera, and if not, requests permission

     @param v The view that was clicked
     */

    public void takePictureNew(View v) {

        if (ContextCompat.checkSelfPermission(RecyclerViewClick.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RecyclerViewClick.this,
                    new String[]{Manifest.permission.CAMERA}, 101);
        }

        /**
         * This method launches the camera app to take a picture and sets the resulting image as the
         * thumbnail image view
         * @param v The view that was clicked
         */
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 101);

            }
        });


    }


    /**

     This method is called when the camera app returns with a picture. It sets the thumbnail image view

     to the resulting image.

     @param requestCode The request code that was used to start the camera app

     @param resultCode The result code that was returned by the camera app

     @param data The intent containing the data returned by the camera app
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            tvImageView3.setImageBitmap(bitmap);

        }

    }

    /**

     This method is called when the "Update Info" button is clicked. It updates the name, location, and purchase link

     of the item in the Firestore database. If a new image was taken, it deletes the old image from Firebase storage and

     uploads the new image.

     @param v The view that was clicked
     */
    public void btnToUpdateInfo(View v) {

        String newName = tvName2.getText().toString();
        String newLocation = tvLocation2.getText().toString();
        String newLink = tvLink2.getText().toString();

        if(newName.isEmpty() || newLocation.isEmpty() || newLink.isEmpty()){

            Toast.makeText(RecyclerViewClick.this, "Please fill in the information!",
                    Toast.LENGTH_SHORT).show();

        }else{

        // Check if the intent contains the item list and position data
        if (getIntent().hasExtra("itemList") && getIntent().hasExtra("itemPos")) {

            String newItemId = selected.getItemImage();

            StorageReference photoRef = storageReference.child("images/" + newItemId);

            // If a new image was taken, delete the old image and upload the new image to Firebase storage
            if (takePicture.isPressed()) {

                photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // image is deleted
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // failed to delete image
                    }
                });

                // Generate a new random key for the new image
                final String newRandomKey = UUID.randomUUID().toString();

                // Get a reference to the new image in Firebase storage
                StorageReference riversRef = storageReference.child("images/" + newRandomKey);

                // Convert the image to bytes and upload to Firebase storage
                tvImageView3.setDrawingCacheEnabled(true);
                tvImageView3.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) tvImageView3.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = riversRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(RecyclerViewClick.this, "Failed to Upload Item to Firebase!",
                                Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(RecyclerViewClick.this, "Successfully Uploaded Item to Firebase!",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                firestore.collection("item").document(selected.getItemID())
                        .update("itemImage", newRandomKey);

            }

            //deleted


            firestore.collection("item").document(selected.getItemID())
                    .update("name", newName);
            firestore.collection("item").document(selected.getItemID())
                    .update("location", newLocation);
            firestore.collection("item").document(selected.getItemID())
                    .update("purchaseLink", newLink);

            Toast.makeText(RecyclerViewClick.this, "Item Updated.",
                    Toast.LENGTH_SHORT).show();

        }

    }


    }


    public void toMainMenu(View v) {
        Intent nextScreen = new Intent(getBaseContext(), MainMenu.class);
        startActivity(nextScreen);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}