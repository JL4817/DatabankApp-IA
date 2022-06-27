package com.example.cs_ia_app.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs_ia_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    //login
    private TextView emailField;
    private TextView passwordField;
    private String userId;

    //spinner
    private LinearLayout layout;
    private EditText nameField;
    private Spinner userRoleSpinner;
    private String selectedRole;

    //admin

    //user


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


    }


    public void logIn(View v){

        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();

        mAuth.signInWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SIGN UP", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            Intent nextScreen = new Intent(getBaseContext(), MainLobby.class);
                            startActivity(nextScreen);

                        } else {

                            // If sign in fails, display a message to the user.
                            Log.w("SIGN UP", "signInWithEmail:failure", task.getException());
                            Toast.makeText(AuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }



    public void signUp(View v){

        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();

        mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("Sign Up", "Succesfully signed up the user");

                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);

                }else{
                    Log.w("Sign up", "User Creation has failed", task.getException());
                    updateUI(null);
                }
            }
        });
    }



        public void updateUI(FirebaseUser currentUser){

            if(currentUser != null){
                Intent intent = new Intent(this, MainLobby.class);
                startActivity(intent);
            }
        }



}