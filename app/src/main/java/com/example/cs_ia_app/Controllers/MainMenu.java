package com.example.cs_ia_app.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs_ia_app.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainMenu extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private TextView tvMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lobby);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        tvMainMenu = findViewById(R.id.mainMenuTV);
        tvMainMenu.setTextColor(Color.rgb(148, 0, 211));

    }


    public void toCreateItem(View v) {

        Intent nextScreen = new Intent(getBaseContext(), CreateItem.class);
        startActivity(nextScreen);

    }


    public void toRec(View v) {

        Intent nextScreen = new Intent(getBaseContext(), ItemInfoActivity.class);
        startActivity(nextScreen);

    }

    public void toFindItem(View v) {
        Intent nextScreen = new Intent(getBaseContext(), FindItemActivity.class);
        startActivity(nextScreen);
    }

    public void toDeleteItem(View v) {
        Intent nextScreen = new Intent(getBaseContext(), DeleteItem.class);
        startActivity(nextScreen);
    }


    public void signUserOut(View v) {

        mAuth.signOut();

        Toast.makeText(MainMenu.this, "User has been logged out!",
                Toast.LENGTH_SHORT).show();

        Intent nextScreen = new Intent(getBaseContext(), LogInActivity.class);
        startActivity(nextScreen);

    }


}