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
import java.util.List;
import java.io.Serializable;

public class ItemInfoActivity extends AppCompatActivity implements RecHolder.ItemClickListener {

    private FirebaseStorage storage;
    private StorageReference storageReference;

    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private RecyclerView recyclerView;
    private RecAdapter itemAdapter;
    private ArrayList<Item> itemList;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retreive_data_recycler_view);

        context = this;
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        recyclerView = findViewById(R.id.recyclerview_id);
        /*
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

         */

        itemList = new ArrayList<>();

      //  itemList = new ArrayList<Item>();
    //    itemAdapter = new RecAdapter(ItemInfoActivity.this, itemList);

        //recyclerView.setAdapter(itemAdapter);

        showVL();
    }


    public void showVL(){

        itemList.clear();
        TaskCompletionSource<String> getAllItem = new TaskCompletionSource<>();
        firestore.collection(Constants.ITEM_COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && task.getResult() != null){
                    for(QueryDocumentSnapshot document : task.getResult()){

                            itemList.add(document.toObject(Item.class));
                    }

                    getAllItem.setResult(null);
                }
                else{
                    Log.d("ItemsInfoActivity", "Error getting comcumets from db: ", task.getException());
                }
            }
        });


        getAllItem.getTask().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {

                RecAdapter myAdapter = new RecAdapter(itemList ,new RecHolder.ItemClickListener() {

                    @Override
                    public void onItemClick(ArrayList<Item> details, int position) {

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


    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onItemClick(ArrayList<Item> details, int position) {

    }


}