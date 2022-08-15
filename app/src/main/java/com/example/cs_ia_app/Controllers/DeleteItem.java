package com.example.cs_ia_app.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cs_ia_app.Models.Item;
import com.example.cs_ia_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DeleteItem extends AppCompatActivity {


    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private Button delete;
    private TextView itemName;

    private ArrayList<Item> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        delete = findViewById(R.id.itemDelete);
        itemName = findViewById(R.id.itemNameDeleteField);

        items = new ArrayList<>();

    }

    public void deleteItem(View v) {


        firestore.collection("item")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                items.add(document.toObject(Item.class));
                            }
                        } else {
                            Log.d("DeleteItem", "Error getting documents: ", task.getException());
                        }


                        for (Item item : items) {

                            String name = itemName.getText().toString();

                            if(name.equals(item.getName())){

                            String documentID = item.getItemID();

                            firestore.collection("item").document(documentID)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("DeleteItemS", "DocumentSnapshot successfully deleted!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("DeleteItemF", "Error deleting document", e);
                                        }
                                    });
                            }
                        }


                    }


                });


    }
}