package com.example.cs_ia_app.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cs_ia_app.Models.Item;
import com.example.cs_ia_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ItemDisplayTool extends AppCompatActivity {


    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private ArrayList<Item> items;


    private TextView locate, nam;

    private String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display_tool);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();

        items = new ArrayList<>();

        locate = findViewById(R.id.plainT);
        nam = findViewById(R.id.plainName);

        getFirebaseData();

    }


    public void getFirebaseData(){

        if(getIntent().hasExtra("hi")){

            value = (String) getIntent().getSerializableExtra("hi");

            System.out.println("Class 2.1 Name is "+value);


        firestore.collection("item")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                items.add(document.toObject(Item.class));
                            }

                                for(Item item: items){

                                //    System.out.println("Class 2.2 Name is "+item.getName());
                                 //   System.out.println("Class 2.3 Name is "+item.getName());

                                    if(value.equals(item.getName())){
                                        String loc = item.getLocation();

                                        nam.setText(loc);
                                        locate.setText(item.getName());
                                    }

                                }

                        } else {
                            Log.d("FindItemActivity", "Error getting documents: ", task.getException());
                        }



                    }
                });

    }
    }

}