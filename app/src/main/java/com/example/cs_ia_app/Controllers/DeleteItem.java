/**

 The DeleteItem class provides functionality to delete an item from Firebase Firestore and Storage.
 */

package com.example.cs_ia_app.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs_ia_app.Models.Item;
import com.example.cs_ia_app.R;
import com.example.cs_ia_app.Utilities.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DeleteItem extends AppCompatActivity {


    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private Button delete;
    private TextView itemName;
    private TextView nameBanner;

    private ArrayList<Item> items;

    /**
     * Initializes the necessary variables and views when the activity is created.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        delete = findViewById(R.id.itemDelete);
        itemName = findViewById(R.id.itemNameDeleteField);
        itemName.setHint("Enter Item Name to Delete Item");
        nameBanner = findViewById(R.id.nameBannerTV);
        nameBanner.setTextColor(Color.rgb(148, 0, 211));
        items = new ArrayList<>();


    }

    /**
     * Deletes an item from Firebase Firestore and Storage.
     *
     * @param v the view that called this method
     */
    public void deleteItem(View v) {

        String name = itemName.getText().toString();

        if(name.isEmpty()){
            // if the name is empty, display an error message in a Toast
            Toast.makeText(DeleteItem.this, "Please fill in the text field!",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            // if the name is not empty, query Firebase Firestore for all items in the collection
            firestore.collection(Constants.ITEM_COLLECTION)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                // if the query is successful, add each item to an ArrayList
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    items.add(document.toObject(Item.class));
                                }
                            } else {
                                // if the query is not successful, log an error message
                                Log.d("DeleteItem", "Error getting documents: ", task.getException());
                            }

                            // iterate over the items in the ArrayList
                            for (Item item : items) { ;
                                // check if the name of the item to be deleted matches the name of an item in the ArrayList
                                if (name.equals(item.getName())) {
                                    // if there is a match, retrieve the document ID of the item
                                    String documentID = item.getItemID();
                                    // delete the item from Firebase Firestore
                                    firestore.collection(Constants.ITEM_COLLECTION).document(documentID)
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    // if the deletion is successful, log a success message and display a success message in a Toast
                                                    Log.d("DeleteItemSuccess", "DocumentSnapshot successfully deleted!");
                                                    Toast.makeText(DeleteItem.this, "Item Has Successfully been deleted!",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                    String imageUri = item.getItemImage();
                                    // get a reference to the Firebase Storage location of the image
                                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                                    StorageReference dateRef = storageRef.child("images/" + imageUri);
                                    // delete the item's image from Firebase Storage
                                    dateRef.delete();

                                }

                            }
                        }
                    });

        }


    }

    /**
     * Returns to the main menu of the application.
     *
     * @param v the view that called this method
     */
    public void toMainMenu(View v) {
        Intent nextScreen = new Intent(getBaseContext(), MainMenu.class);
        startActivity(nextScreen);
    }


}