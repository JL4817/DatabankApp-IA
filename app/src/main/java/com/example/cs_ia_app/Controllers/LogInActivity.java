package com.example.cs_ia_app.Controllers;

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

import com.example.cs_ia_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class LogInActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private FirebaseUser mUser;

    private TextView nameFieldtv;
    private TextView passwordFieldtv;
    private TextView banner1, banner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();

        nameFieldtv = findViewById(R.id.accountNameTV);
        passwordFieldtv = findViewById(R.id.accountPasswordTV);
        passwordFieldtv.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        //changing to purple color, textview
        banner1 = findViewById(R.id.textView3);
        banner1.setTextColor(Color.rgb(148, 0, 211));

        banner2 = findViewById(R.id.textView4);
        banner2.setTextColor(Color.rgb(148, 0, 211));

    }


    public void toSignUpPage(View v) {
        Intent nextScreen = new Intent(getBaseContext(), SignUpActivity.class);
        startActivity(nextScreen);
    }


    public void logIn(View v) {

        //make the if statement in login

        String emailString = nameFieldtv.getText().toString();
        String passwordString = passwordFieldtv.getText().toString();

        mAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SIGN UP", "signInWithEmail:success");


                    try {

                        TimeUnit.SECONDS.sleep(1);
                        //  Thread.sleep(500); //millisecond

                        String userUID = mUser.getUid();

                        firestore.collection("user").document(userUID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
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

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }


                }
            }
        });
    }


    public void updateUI(FirebaseUser currentUser) {

        if (currentUser != null) {
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
        }
    }


}