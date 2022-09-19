package com.example.cs_ia_app.Controllers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs_ia_app.Models.Admin;
import com.example.cs_ia_app.Models.Item;
import com.example.cs_ia_app.Models.User;
import com.example.cs_ia_app.R;
import com.example.cs_ia_app.Utilities.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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
    private Button disableUser;

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
        disableUser = findViewById(R.id.btnDisableOwner);

        getFirebaseData();

    }


    public void getFirebaseData() {

        if (getIntent().hasExtra("selected_item")) {

            Item data = (Item) getIntent().getSerializableExtra("selected_item");
            String owner = data.getOwner();
            value = (String) getIntent().getSerializableExtra("nameValue");

            firestore.collection("item")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    items.add(document.toObject(Item.class));
                                }

                                for (Item item : items) {

                                    if (value.equals(item.getName())) {

                                        String loc = item.getLocation();
                                        String pLink = item.getPurchaseLink();
                                        String imageUri = item.getItemImage();

                                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                                        StorageReference dateRef = storageRef.child("images/" + imageUri);
                                        dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri downloadUrl) {
                                                Picasso.get().load(downloadUrl).into(iv);
                                            }
                                        });

                                        link.setText("Purchase Link: " + pLink);
                                        locate.setText("Location: " + loc);
                                        nam.setText("Name: " + item.getName());

                                    }

                                }

                            } else {
                                Log.d("FindItemActivity", "Error getting documents: ", task.getException());
                            }


                        }
                    });


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


                            for (User user : users) {

                                if (owner.equals(user.getUserID())) {

                                    //  System.out.println("HERE IS THE OWNER"+a);
                                    String ownerName = user.getName();

                                    tvOwnerName.setText("Owner Name: " + ownerName);


                                    firestore.collection("user").document(mUser.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                                            String currentUserType = value.getString("userType").toString();

                                            if (currentUserType.equals(Constants.USER)) {

                                                disableUser.setVisibility(View.GONE);

                                            } else if (currentUserType.equals(Constants.ADMIN)) {

                                                boolean adminUserBoolean = value.getBoolean("canDisableUsers").booleanValue();

                                                if (adminUserBoolean == true) {

                                                    disableUser.setVisibility(View.VISIBLE);
                                                }

                                            }

                                        }
                                    });

                                }

                            }


                        }
                    });
        }
    }


    public void toDisableUser(View v) {

        if (getIntent().hasExtra("selected_item")) {

            value = (String) getIntent().getSerializableExtra("nameValue");
            Item data = (Item) getIntent().getSerializableExtra("selected_item");

            firestore.collection("user")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    users.add(document.toObject(User.class));
                                }

                                for (User user : users) {

                                    if(data.getOwner().equals(user.getUserID())){

                                        firestore.collection("item").document(data.getOwner())
                                                .update("isValid", false);
                                    }


                                    if(user.getIsValid() == false){
                                        firestore.collection("user").document(data.getOwner()).delete();
                                    }

                                }

                            } else {
                                Log.d("ItemDisplayTool", "Error getting documents: ", task.getException());
                            }

                        }
                    });
        }

    }


    public void toMainMenu(View v){
        Intent nextScreen = new Intent(getBaseContext(), MainMenu.class);
        startActivity(nextScreen);
    }

}