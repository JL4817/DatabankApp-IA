package com.example.cs_ia_app.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cs_ia_app.Models.Item;
import com.example.cs_ia_app.Models.User;
import com.example.cs_ia_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemDisplayTool extends AppCompatActivity {


    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private ArrayList<Item> items;

    private ArrayList<User> users;


    private TextView locate, nam, link;
    private String value;
    private ImageView iv;

    private TextView tvOwnerName;
    private String ownerN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display_tool);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();

        items = new ArrayList<>();
        users = new ArrayList<>();

        locate = findViewById(R.id.lvLocation);
        nam = findViewById(R.id.lvName);
        link = findViewById(R.id.lvLink);
        iv = findViewById(R.id.lvImageView);
        tvOwnerName = findViewById(R.id.lvOwnerName);

        getFirebaseData();

    }


    public void getFirebaseData(){

        if(getIntent().hasExtra("hi")){

            value = (String) getIntent().getSerializableExtra("hi");

            //System.out.println("Class 2.1 Name is "+value);

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
                                        String pLink = item.getPurchaseLink();


                                        String imageUri = item.getItemImage();
                                        // Points to the root reference
                                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                                        StorageReference dateRef = storageRef.child("images/" + imageUri);
                                        dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                                        {
                                            @Override
                                            public void onSuccess(Uri downloadUrl)
                                            {
                                                Picasso.get().load(downloadUrl).into(iv);
                                            }
                                        });

                                        link.setText("Purchase Link: "+pLink);
                                        locate.setText("Location: "+loc);
                                        nam.setText("Name: "+item.getName());

                                    }

                                }

                        } else {
                            Log.d("FindItemActivity", "Error getting documents: ", task.getException());
                        }



                    }
                });


            if(getIntent().hasExtra("ownerName")){

                ownerN = (String) getIntent().getSerializableExtra("ownerName");

                firestore.collection("user")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        users.add(document.toObject(User.class));
                                    }
                                } else {
                                    Log.d("ItemDisplayTool", "Error getting documents: ", task.getException());
                                }


                                for(User user: users){

                                    if(ownerN.equals(user.getUserID())){
                                        String ownerName = user.getName();
                                        tvOwnerName.setText("Owner Name: "+ownerName);
                                    }

                                }


                            }
                        });
            }




         }
    }

}