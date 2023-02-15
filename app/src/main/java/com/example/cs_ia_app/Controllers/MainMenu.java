/**

 This is the MainMenu class for the CS-IA App.
 It is responsible for the main menu screen of the app
 and the functionality of the buttons on this screen.
 */
package com.example.cs_ia_app.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs_ia_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainMenu extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private TextView tvMainMenu;

    /**
     * Called when the activity is starting or restarting.
     * It initializes the FirebaseAuth and FirebaseFirestore objects
     * and sets the color of the text of the TextView tvMainMenu.
     *
     * @param savedInstanceState a Bundle object containing the activity's previously saved state.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lobby);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        tvMainMenu = findViewById(R.id.mainMenuTV);
        tvMainMenu.setTextColor(Color.rgb(148, 0, 211));

    }


    /**
     * Called when the user taps on the "Create Item" button.
     * It starts the CreateItem activity.
     *
     * @param v the View object of the button that was clicked.
     */
    public void toCreateItem(View v) {
        Intent nextScreen = new Intent(getBaseContext(), CreateItem.class);
        startActivity(nextScreen);
    }

    /**
     * Called when the user taps on the "View Items" button.
     * It starts the ItemInfoActivity activity.
     *
     * @param v the View object of the button that was clicked.
     */
    public void toRec(View v) {
        Intent nextScreen = new Intent(getBaseContext(), ItemInfoActivity.class);
        startActivity(nextScreen);
    }

    /**
     * Called when the user taps on the "Find Item" button.
     * It starts the FindItemActivity activity.
     *
     * @param v the View object of the button that was clicked.
     */
    public void toFindItem(View v) {
        Intent nextScreen = new Intent(getBaseContext(), FindItemActivity.class);
        startActivity(nextScreen);
    }

    /**
     * Called when the user taps on the "Delete Item" button.
     * It starts the DeleteItem activity.
     *
     * @param v the View object of the button that was clicked.
     */
    public void toDeleteItem(View v) {
        Intent nextScreen = new Intent(getBaseContext(), DeleteItem.class);
        startActivity(nextScreen);
    }


    /**
     * Called when the user taps on the "Log Out" button.
     * It signs the user out, shows a Toast message to confirm the user has been logged out,
     * and starts the LogInActivity activity.
     *
     * @param v the View object of the button that was clicked.
     */
    public void signUserOut(View v) {

        mAuth.signOut();

        Toast.makeText(MainMenu.this, "User has been logged out!",
                Toast.LENGTH_SHORT).show();

        Intent nextScreen = new Intent(getBaseContext(), LogInActivity.class);
        startActivity(nextScreen);

    }


}