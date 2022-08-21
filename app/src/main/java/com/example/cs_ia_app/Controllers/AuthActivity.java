package com.example.cs_ia_app.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs_ia_app.Models.Admin;
import com.example.cs_ia_app.Models.Person;
import com.example.cs_ia_app.Models.User;
import com.example.cs_ia_app.R;
import com.example.cs_ia_app.Utilities.Constants;
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
    private Switch canDisableUsers;

    //user
    private EditText occupation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        layout = findViewById(R.id.llUser);
        userRoleSpinner = findViewById(R.id.spnAuthActivity);
        setupSpinner();

    }

    private void setupSpinner(){
        String[] userTypes = {Constants.USER, Constants.ADMIN};
        // add user types to spinner
        ArrayAdapter<String> langArrAdapter = new ArrayAdapter<String>(AuthActivity.this,
                android.R.layout.simple_spinner_item, userTypes);
        langArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userRoleSpinner.setAdapter(langArrAdapter);

        //triggered whenever user selects something different
        userRoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedRole = parent.getItemAtPosition(position).toString();
                addFields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    public void addFields(){

        commonFields();
        if(selectedRole.equals("User")){
            occupation = new EditText(this);
            occupation.setHint("Enter Occupation");
            layout.addView(occupation);
        }

        if(selectedRole.equals("Admin")){
            canDisableUsers = new Switch(this);
            canDisableUsers.setHint("Can disable Users");
            layout.addView(canDisableUsers);
        }
    }

    public void commonFields(){
        layout.removeAllViewsInLayout();
        nameField = new EditText(this);
        nameField.setHint("Name");
        layout.addView(nameField);
        emailField = new EditText(this);
        emailField.setHint("Email");
        layout.addView(emailField);
        passwordField = new EditText(this);
        passwordField.setHint("Password");
        layout.addView(passwordField);
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

                            Intent nextScreen = new Intent(getBaseContext(), MainMenu.class);
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
        String nameString = nameField.getText().toString();

        mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("Sign Up", "Succesfully signed up the user");

                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    userId = user.getUid();
                    addUserToDatabase(emailString, nameString);

                }else{
                    Log.w("Sign up", "Account creation failed", task.getException());
                    updateUI(null);
                }
            }
        });


    }


    public void addUserToDatabase(String emailString, String nameString){

        //make new user according to selected usertype
        Person newPerson = null;
        String userType = selectedRole;


        if(selectedRole.equals(Constants.USER)) {
            String job = occupation.getText().toString();
            newPerson = new User(nameString, emailString, userId, job, userType);
        }

        if(selectedRole.equals(Constants.ADMIN)) {
            Boolean stopUser = canDisableUsers.isChecked();
            newPerson = new Admin(nameString, emailString, userId, stopUser, userType);
        }

        //add the new user to the database
        firestore.collection(Constants.USER_COLLECTION).document(userId).set(newPerson);

    }



        public void updateUI(FirebaseUser currentUser){

            if(currentUser != null){
                Intent intent = new Intent(this, MainMenu.class);
                startActivity(intent);
            }
        }



}