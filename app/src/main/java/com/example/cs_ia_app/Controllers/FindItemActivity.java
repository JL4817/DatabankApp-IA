package com.example.cs_ia_app.Controllers;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.cs_ia_app.Models.Item;
import com.example.cs_ia_app.R;
import com.example.cs_ia_app.Utilities.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FindItemActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private Context context;

    private ListView listView;
    private ArrayList<Item> items;
    public ArrayAdapter<String> arrayAdapter;
    ArrayList<String> itemName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_item);

        context = this;
        firestore = FirebaseFirestore.getInstance();
        listView = findViewById(R.id.listview);


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, itemName);
        listView.setAdapter(arrayAdapter);
        items = new ArrayList<>();
        getNames();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                firestore.collection(Constants.ITEM_COLLECTION)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        items.add(document.toObject(Item.class));
                                    }
                                } else {
                                    Log.d("FindItemActivity", "Error getting documents: ", task.getException());
                                }

                                Intent i = new Intent(context, ItemDisplayTool.class);
                                i.putExtra("selected_item", items.get(position));
                                startActivity(i);
                            }
                        });
            }
        });
    }


    public void getNames() {

        firestore.collection(Constants.ITEM_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                items.add(document.toObject(Item.class));
                            }
                        } else {
                            Log.d("FindItemActivity", "Error getting documents: ", task.getException());
                        }

                        for (Item item : items) {

                            itemName.add(item.getName());
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search for Item Name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                arrayAdapter.getFilter().filter(newText);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.to_main_menu:
                Intent nextScreen = new Intent(getBaseContext(), MainMenu.class);
                startActivity(nextScreen);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
