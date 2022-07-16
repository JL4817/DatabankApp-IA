package com.example.cs_ia_app.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cs_ia_app.R;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lobby);
    }


    public void toCreateItem(View v){

        Intent nextScreen = new Intent(getBaseContext(), CreateItem.class);
        startActivity(nextScreen);

    }


    public void toRec(View v){

        Intent nextScreen = new Intent(getBaseContext(), ItemInfoActivity.class);
        startActivity(nextScreen);

    }






}