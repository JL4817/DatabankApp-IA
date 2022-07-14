package com.example.cs_ia_app.Controllers;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cs_ia_app.Models.Item;
import com.example.cs_ia_app.R;
import com.example.cs_ia_app.Utilities.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class DisplayImageTest extends AppCompatActivity {


    ImageView imageViewPictureDisplay;
    Button buttonToFirebase;

    public Uri imageUri;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    private ArrayList<Item> itemsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image_test);

        imageViewPictureDisplay = findViewById(R.id.ivItemPictureView);
        buttonToFirebase = findViewById(R.id.btnGetFromFirebase);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        firestore = FirebaseFirestore.getInstance();

    }



    public void displayImage(View v) {

        //failure


        //making the ID of the User as the image id,
        firestore.collection(Constants.ITEM_COLLECTION).whereEqualTo("itemImage", "").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {


                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                storageRef.child("images/" +  firestore.collection(Constants.ITEM_COLLECTION).document("itemID").get()).getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                /*

                                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                                    imageViewPictureDisplay.setImageBitmap(bitmap);

                                    imageUri = data.getData();

                                 */

                                    imageViewPictureDisplay.setImageURI(imageUri);

                                }

                        });

            }

        });



    }





}