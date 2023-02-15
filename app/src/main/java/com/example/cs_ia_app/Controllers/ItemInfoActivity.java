/**

 This class represents the activity that displays a list of items in a RecyclerView.
 */

package com.example.cs_ia_app.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.cs_ia_app.Models.Item;
import com.example.cs_ia_app.R;
import com.example.cs_ia_app.Utilities.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ItemInfoActivity extends AppCompatActivity implements RecHolder.ItemClickListener {

    private FirebaseFirestore firestore; // Firebase Firestore instance
    private RecyclerView recyclerView; // RecyclerView that displays items
    private ArrayList<Item> itemList; // List of items to display in the RecyclerView
    private Context context; // Context of this activity


    /**
     * Called when the activity is starting. Sets up the RecyclerView to display items.
     *
     * @param savedInstanceState the saved instance state of the activity
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retreive_data_recycler_view);

        context = this;
        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerview_id);
        itemList = new ArrayList<>();

        showItemList();
    }


    /**
     * Retrieves all items from Firebase Firestore and displays them in the RecyclerView.
     */

    public void showItemList() {

        itemList.clear();

        // Creates a task to get all items from the Firestore database
        TaskCompletionSource<String> getAllItem = new TaskCompletionSource<>();
        firestore.collection(Constants.ITEM_COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    // Add each item to the itemList ArrayList
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        itemList.add(document.toObject(Item.class));
                    }

                    getAllItem.setResult(null);
                } else {
                    // Log an error if the task fails
                    Log.d("ItemsInfoActivity", "Error getting documents from db: ", task.getException());
                }
            }
        });

        // Once the task to get all items is complete, create a RecyclerView adapter and set it to the RecyclerView
        getAllItem.getTask().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {

                RecAdapter myAdapter = new RecAdapter(itemList, new RecHolder.ItemClickListener() {

                    @Override
                    public void onItemClick(ArrayList<Item> details, int position) {
                        // Start a new activity when an item in the RecyclerView is clicked
                        Intent i = new Intent(context, RecyclerViewClick.class);
                        i.putExtra("itemList", itemList);
                        i.putExtra("itemPos", position);
                        startActivity(i);

                    }

                });

                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ItemInfoActivity.this));
            }
        });
    }

    /**

     Notifies the listener that the pointer capture state has changed.
     @param hasCapture true if the pointer is currently captured, false otherwise
     */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**

     Called when an item in the RecyclerView is clicked.
     @param details the details of the clicked item
     @param position the position of the clicked item in the list
     */
    @Override
    public void onItemClick(ArrayList<Item> details, int position) {

    }


}