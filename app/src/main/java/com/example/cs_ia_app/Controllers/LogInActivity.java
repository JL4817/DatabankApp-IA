/**

 This class represents the log in activity of the application.
 */

package com.example.cs_ia_app.Controllers;

import static com.google.common.io.Files.getFileExtension;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs_ia_app.Models.User;
import com.example.cs_ia_app.R;
import com.example.cs_ia_app.Utilities.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


import java.util.ArrayList;

public class LogInActivity extends AppCompatActivity {

    // Firebase authentication and database instances
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private FirebaseUser mUser;

    // TextView fields
    private TextView nameFieldtv;
    private TextView passwordFieldtv;
    private TextView banner1, banner2;
    private ArrayList<User> users;


    /**
     * Sets up the LogInActivity layout and initializes the FirebaseAuth and FirebaseFirestore instances.
     * @param savedInstanceState A Bundle object containing the activity's previous saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        nameFieldtv = findViewById(R.id.accountNameTV);
        passwordFieldtv = findViewById(R.id.accountPasswordTV);
        passwordFieldtv.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        // changing banner colors to purple
        banner1 = findViewById(R.id.textView3);
        banner1.setTextColor(Color.rgb(148, 0, 211));

        banner2 = findViewById(R.id.textView4);
        banner2.setTextColor(Color.rgb(148, 0, 211));
        mUser = mAuth.getCurrentUser();
        users = new ArrayList<>();


    }

    /**
     * Starts the SignUpActivity when the user clicks the 'Sign Up' button.
     * @param v The view that was clicked.
     */
    public void toSignUpPage(View v) {
        Intent nextScreen = new Intent(getBaseContext(), SignUpActivity.class);
        startActivity(nextScreen);
    }


    /**
     * Authenticates the user and logs them in when the 'Log In' button is clicked.
     * @param v The view that was clicked.
     */
    public void logIn(View v) {

        String emailString = nameFieldtv.getText().toString();
        String passwordString = passwordFieldtv.getText().toString();

        mAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                mUser = mAuth.getCurrentUser();

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SIGN UP", "signInWithEmail:success");


                        String userUID = mUser.getUid();

                        firestore.collection(Constants.USER_COLLECTION).document(userUID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                                boolean userValidation = value.getBoolean("isValid").booleanValue();

                                //checks if user is valid
                                if (userValidation == true) {

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);

                                    Intent nextScreen = new Intent(getBaseContext(), MainMenu.class);
                                    startActivity(nextScreen);

                                    Toast.makeText(LogInActivity.this, "Log In Successful!",
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    Log.w("SIGN UP", "signInWithEmailAndPassword:Failure", task.getException());
                                    Toast.makeText(LogInActivity.this, "user is invalid, can not log in!",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);

                                }
                            }
                        });

                }
            }
        });


    }


    /**

     Updates the user interface based on the current Firebase user. If the user is not null,

     navigates to the main menu screen by creating an intent for MainMenu activity and starting it.

     @param currentUser the current Firebase user
     */
    public void updateUI(FirebaseUser currentUser) {

        // Create an intent to navigate to the main menu
        if (currentUser != null) {
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
        }
    }


}