package com.example.cs_ia_app.Controllers;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cs_ia_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

public class DisplayImageTest extends AppCompatActivity {


    ImageView imageViewPictureDisplay;
    Button buttonToFirebase;

    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private FirebaseStorage storage;
    private StorageReference storageReference;


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

    }



    public void displayImage(View v) {

        //making the ID of the User as the image id,



    }




}