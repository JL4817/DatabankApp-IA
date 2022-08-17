package com.example.cs_ia_app.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cs_ia_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainMenu extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lobby);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }


    public void toCreateItem(View v){

        Intent nextScreen = new Intent(getBaseContext(), CreateItem.class);
        startActivity(nextScreen);

    }


    public void toRec(View v){

        Intent nextScreen = new Intent(getBaseContext(), ItemInfoActivity.class);
        startActivity(nextScreen);

    }

    public void toFindItem(View v){
        Intent nextScreen = new Intent(getBaseContext(), FindItemActivity.class);
        startActivity(nextScreen);
    }

    public void toDeleteItem(View v){
        Intent nextScreen = new Intent(getBaseContext(), DeleteItem.class);
        startActivity(nextScreen);
    }


    public void signUserOut(View v){

        mAuth.signOut();

        showToast("User Logged Out");

        Intent nextScreen = new Intent(getBaseContext(), AuthActivity.class);
        startActivity(nextScreen);

    }


    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }




}