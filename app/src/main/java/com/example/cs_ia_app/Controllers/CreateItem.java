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
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cs_ia_app.Models.Item;
import com.example.cs_ia_app.R;
import com.example.cs_ia_app.Utilities.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class CreateItem extends AppCompatActivity {

    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    ImageView imageView;
    Button button;
    TextView id;

    Button buttonFire;

    EditText nameItem;
    EditText locationItem;
    EditText purchaseLinkItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        imageView = findViewById(R.id.ivItemDisplay);
        button = findViewById(R.id.btnGetFromFirebase);
        id = findViewById(R.id.etItemID);

        buttonFire = findViewById(R.id.btnFirebase);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //attributes
        nameItem = findViewById(R.id.etItemName);
        locationItem = findViewById(R.id.etItemLocation);
        purchaseLinkItem = findViewById(R.id.etItemLink);


    }


    public void saveImage(View v) {

        if (ContextCompat.checkSelfPermission(CreateItem.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CreateItem.this,
                    new String[]{Manifest.permission.CAMERA}, 101);

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 101);

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            imageUri = data.getData();

            //ImageView imageView = findViewById(R.id.image_view);
            //  Uri selectedImage = data.getData();
            //    imageView.setImageURI(imageUri);
        }

    }


    public void toFirebase(View v) {

        final String randomKey = UUID.randomUUID().toString();

        String location = locationItem.getText().toString();
        String name = nameItem.getText().toString();
        String link = purchaseLinkItem.getText().toString();

        if (location.isEmpty() || name.isEmpty() || link.isEmpty()) {

            Toast.makeText(CreateItem.this, "Please fill in the text field!",
                    Toast.LENGTH_LONG).show();

        } else {

            StorageReference imageRef = storageReference.child("images/" + randomKey);

            // Get the data from an ImageView as bytes
            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = imageRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(CreateItem.this, "Failed to Upload Item to Firebase!",
                            Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(CreateItem.this, "Successfully Uploaded Item to Firebase!",
                            Toast.LENGTH_SHORT).show();
                }
            });

            id.setText(randomKey);

            //generate + get new key
            DocumentReference newSignUpKey = firestore.collection(Constants.ITEM_COLLECTION).document();
            String itemKey = newSignUpKey.getId();

            //user ID
            String userID = mUser.getUid();

            Item newItem = null;

            newItem = new Item(itemKey, location, name, link, randomKey, userID);

            //add the new item to the database
            newSignUpKey.set(newItem);

            firestore.collection(Constants.ITEM_COLLECTION).document(itemKey).set(newItem);
            firestore.collection(Constants.USER_COLLECTION).document(mUser.getUid())
                    .update("ownedItems", FieldValue.arrayUnion(randomKey));

        }

    }




    public void toMainMenu(View v) {
        Intent nextScreen = new Intent(getBaseContext(), MainMenu.class);
        startActivity(nextScreen);
    }


}