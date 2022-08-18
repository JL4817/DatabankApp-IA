package com.example.cs_ia_app.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.cs_ia_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class EditItem extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

    }


    public void updateItem(View v){

/*
        firestore.collection("item")
                .document("frank")
                .update({
                        "age": 13,
                "favorites.color": "Red"
                });

 */




    }







}