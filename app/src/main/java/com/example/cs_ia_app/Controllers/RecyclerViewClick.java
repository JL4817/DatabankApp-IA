package com.example.cs_ia_app.Controllers;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cs_ia_app.Models.Item;
import com.example.cs_ia_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class RecyclerViewClick extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore firestore;


    private ImageView imageView;
    private TextView tvlocation, tvname, tvID, tvitemLink;
    private String location, name, ID, link, image;

    private ArrayList<Item> itemList;
    private int position;
    private Item selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.design_row_for_recyclerview);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        if (getIntent().hasExtra("itemList") && getIntent().hasExtra("itemPos")) {

            itemList = (ArrayList<Item>) getIntent().getSerializableExtra("itemList");
            position = (int) getIntent().getSerializableExtra("itemPos");


            selected = itemList.get(position);
            tvlocation = findViewById(R.id.etItemLocationDisplay);
            tvname = findViewById(R.id.etItemNameDisplay);
            tvID = findViewById(R.id.etItemIDDisplay);
            tvitemLink = findViewById(R.id.etItemLinkDisplay);

            imageView = findViewById(R.id.ivItemShow);


            location = selected.getLocation();
            name = selected.getName();
            ID = selected.getItemID();
            link = selected.getPurchaseLink();

            tvlocation.setText("Location: " + location);
            tvname.setText("Price: " + name);
            tvID.setText("Model: " + ID);
            tvitemLink.setText("Vehicle ID: " + link);


            String imageUri = selected.getItemImage();
            Picasso.get().load(imageUri).into(imageView);


           // imageView.setImageURI(image);

        }


    }




    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}