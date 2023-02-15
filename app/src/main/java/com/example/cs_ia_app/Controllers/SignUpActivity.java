/**

 This class represents the SignUp activity in the app.
 It allows a new user to sign up for an account with their name, email and password, and choose whether to
 be an admin or regular user. The Admin user can also choose to be able to disable other users.
 */
package com.example.cs_ia_app.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private FirebaseUser mUser;

    //login
    private TextView emailField;
    private TextView passwordField;
    private String userId;
    private boolean validUser = true;

    //spinner
    private LinearLayout layout;
    private EditText nameField;
    private Spinner userRoleSpinner;
    private String selectedRole;

    //admin
    private Switch canDisableUsers;

    //user
    private EditText occupation;
    private ArrayList<Admin> admins;

    /**
     * This method initializes the SignUpActivity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();

        admins = new ArrayList<>();
        layout = findViewById(R.id.llUser);
        userRoleSpinner = findViewById(R.id.spnAuthActivity);
        setupSpinner();


    }

    /**
     * This method sets up the userRoleSpinner for the SignUpActivity.
     */
    private void setupSpinner() {
        String[] userTypes = {Constants.USER, Constants.ADMIN};
        // add user types to spinner
        ArrayAdapter<String> langArrAdapter = new ArrayAdapter<String>(SignUpActivity.this,
                android.R.layout.simple_spinner_item, userTypes);
        langArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userRoleSpinner.setAdapter(langArrAdapter);

        //triggered whenever user selects something different
        userRoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRole = parent.getItemAtPosition(position).toString();
                addFields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * This method adds fields to the activity based on the user's selected role.
     */
    public void addFields() {

        commonFields();
        if (selectedRole.equals("User")) {
            occupation = new EditText(this);
            occupation.setHint("Enter Occupation");
            layout.addView(occupation);
        }

        if (selectedRole.equals("Admin")) {
            canDisableUsers = new Switch(this);
            canDisableUsers.setHint("Can disable Users");
            layout.addView(canDisableUsers);
        }
    }

    public void commonFields() {
        layout.removeAllViewsInLayout();
        nameField = new EditText(this);
        nameField.setHint("Name");
        layout.addView(nameField);
        emailField = new EditText(this);
        emailField.setHint("Email");
        layout.addView(emailField);
        passwordField = new EditText(this);
        passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordField.setHint("Password");
        layout.addView(passwordField);
    }


    /**
     * Method to sign up the user with provided details.
     * It checks for the number of existing admin users and prevents more than 3 admin users to sign up.
     */
    public void signUp(View v) {

        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();
        String nameString = nameField.getText().toString();

        firestore.collection(Constants.USER_COLLECTION).whereEqualTo("userType", "Admin")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful() && task.getResult() != null){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        admins.add(document.toObject(Admin.class));
                    }
                }

            }
        });


        if(admins.size() > 3 && selectedRole.equals("Admin")){

            Toast.makeText(SignUpActivity.this, "Too many Admin Users!",
                    Toast.LENGTH_LONG).show();

        }else{

            if (nameString.isEmpty() || emailString.isEmpty() || passwordString.isEmpty()) {

                Toast.makeText(SignUpActivity.this, "Please fill in the text field",
                        Toast.LENGTH_LONG).show();
            } else {

                mAuth.createUserWithEmailAndPassword(emailString, passwordString)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d("Sign Up", "Succesfully signed up the user");

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                    userId = user.getUid();
                                    addUserToDatabase(emailString, nameString);

                                } else {
                                    Log.w("Sign up", "Account creation failed", task.getException());
                                    updateUI(null);
                                }
                            }
                        });
            }
        }
    }


    /**

     This method adds a new user to the database according to their selected usertype.

     @param emailString A string representing the user's email.

     @param nameString A string representing the user's name.
     */
    public void addUserToDatabase(String emailString, String nameString) {

        //make new user according to selected usertype
        Person newPerson = null;
        String userType = selectedRole;


        if (selectedRole.equals(Constants.USER)) {
            String job = occupation.getText().toString();
            newPerson = new User(nameString, emailString, userId, job, userType, validUser);
        }

        if (selectedRole.equals(Constants.ADMIN)) {
            Boolean stopUser = canDisableUsers.isChecked();
            newPerson = new Admin(nameString, emailString, userId, stopUser, userType, validUser);
        }

        //add the new user to the database
        firestore.collection(Constants.USER_COLLECTION).document(userId).set(newPerson);

    }


    /**
     * Called when the user taps on the "Log In" button.
     *
     * @param v the View object of the button that was clicked.
     */
    public void toLogInScreen(View v) {

        Intent nextScreen = new Intent(getBaseContext(), LogInActivity.class);
        startActivity(nextScreen);

    }


    /**

     This method updates the UI based on the current user. If the user is logged in, the MainMenu activity is launched.

     @param currentUser The current FirebaseUser object.
     */
    public void updateUI(FirebaseUser currentUser) {

        if (currentUser != null) {
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
        }
    }


}