/**

 The ItemDisplayTool activity displays detailed information about a single Item, including its name, image,
 location, purchase link, and owner name. If the current user is an administrator, a button to disable the item's owner is also displayed.
 The information about the item is retrieved from Firebase, and the image is displayed using the Picasso library.
 This activity expects an Item object to be passed as an extra with the name "selected_item".
 */

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
    private ImageView iv;
    private TextView tvOwnerName;
    private Button disableUser;

    /**
     * Initializes the activity layout and Firebase objects. Calls getFirebaseData() to retrieve data from Firebase and display it.
     *
     * @param savedInstanceState A Bundle object containing the activity's previously saved state.
     */

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

    /**
     * Retrieves data from Firebase and uses it to display information about the item and its owner. If the current user is an admin,
     * displays the disable button for the item's owner.
     */

    public void getFirebaseData() {

        if (getIntent().hasExtra("selected_item")) {

            Item data = getIntent().getParcelableExtra("selected_item");
            String owner = data.getOwner();

            //Retrieves all items from Firestore and adds them to an ArrayList
            firestore.collection(Constants.ITEM_COLLECTION)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    items.add(document.toObject(Item.class));
                                }
                                //Iterates through all items in the "items" ArrayList
                                for (Item item : items) {
                                    //Checks if the current item's name matches the selected item's name
                                    if (data.getName().equals(item.getName())) {

                                        String loc = item.getLocation();
                                        String pLink = item.getPurchaseLink();
                                        String imageUri = item.getItemImage();
                                        //Loads the image for the selected item into the ImageView using Picasso library
                                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                                        StorageReference dateRef = storageRef.child("images/" + imageUri);
                                        dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri downloadUrl) {
                                                Picasso.get().load(downloadUrl).into(iv);
                                            }
                                        });
                                        //Sets the text for the location, purchase link, and item name textviews
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

            //Retrieves all users from Firestore and adds them to an ArrayList
            firestore.collection(Constants.USER_COLLECTION)
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

                            //Iterates through all users in the "users" ArrayList
                            for (User user : users) {
                                //Checks if the current user's ID matches the owner ID of the selected item
                                if (owner.equals(user.getUserID())) {

                                    String ownerName = user.getName();

                                    tvOwnerName.setText("Owner Name: " + ownerName);
                                    //Listens for changes to the current user's Firestore document
                                    firestore.collection(Constants.USER_COLLECTION).document(mUser.getUid())
                                            .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                                                    String currentUserType = value.getString("userType");

                                                    //checks if overall Person is either a user or an admin
                                                    if (currentUserType.equals(Constants.USER)) {

                                                        disableUser.setVisibility(View.GONE);

                                                    //if user is admin
                                                    } else if (currentUserType.equals(Constants.ADMIN)) {

                                                        boolean adminUserBoolean = value.getBoolean("disableUsers").booleanValue();
                                                        //checks if the unique admin variable is true
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

    /**

     Disables the selected user by setting their "isValid" field to false in the Firestore database.

     If the intent passed to this activity has the "selected_item" extra, the corresponding user will be disabled.

     @param v the View object associated with the method call
     */
    public void toDisableUser(View v) {

        if (getIntent().hasExtra("selected_item")) {

            Item data = getIntent().getParcelableExtra("selected_item");

            // Get all users from Firestore and disable the selected user
            firestore.collection(Constants.USER_COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        // Add all users to an array
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            users.add(document.toObject(User.class));
                        }

                        for (User user : users) {

                            // Disable the selected user
                            if (data.getOwner().equals(user.getUserID())) {

                                firestore.collection("user").document(data.getOwner())
                                        .update("isValid", false);

                                Toast.makeText(ItemDisplayTool.this, "User Disabled!!",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }


                    } else {
                        Log.d("ItemDisplayTool", "Error getting documents: ", task.getException());
                    }

                }
            });
        }

    }

    /**

     Navigates to the main menu.
     @param v the View object associated with the method call
     */
    public void toMainMenu(View v) {
        Intent nextScreen = new Intent(getBaseContext(), MainMenu.class);
        startActivity(nextScreen);
    }

}