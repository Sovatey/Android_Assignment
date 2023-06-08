package com.example.assingment_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupActivity extends AppCompatActivity {

    EditText signup_username,signup_password,signup_email,signup_confirm_pass;
    Button signup_button;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressDialog = new ProgressDialog(this);

        signup_username = (EditText) findViewById(R.id.signup_username);
        signup_email = (EditText) findViewById(R.id.signup_email);
        signup_password = (EditText) findViewById(R.id.signup_password);
        signup_confirm_pass = (EditText) findViewById(R.id.signup_confirm_password);
        signup_button = (Button) findViewById(R.id.signup_button);

        mAuth = FirebaseAuth.getInstance();

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = signup_username.getText().toString();
                String password = signup_password.getText().toString();
                String confirmPass = signup_confirm_pass.getText().toString();
                String email = signup_email.getText().toString();

                if (username.equals("") || password.equals("") || email.equals("") || confirmPass.equals("")) {
                    Toast.makeText(SignupActivity.this, "Please enter all of these fields", Toast.LENGTH_SHORT).show();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(SignupActivity.this, "Please enter an valid email", Toast.LENGTH_SHORT).show();
                } else if(!password.equalsIgnoreCase(confirmPass)) {
                    Toast.makeText(SignupActivity.this, "Confirm password does not match" + password + " " + confirmPass, Toast.LENGTH_SHORT).show();
                } else {
                    createUser(email, password, username);
                }
            }
        });
    }

    private void createUser(String email, String password, String username) {

        progressDialog.setMessage("Creating user...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        // Mark: Update username
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build();
                        user.updateProfile(profileUpdates);

                        // Mark: Send verification link to mail
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if(progressDialog.isShowing()) progressDialog.dismiss();
                                    dialogSignupSuccess();
                                }
                            });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if(progressDialog.isShowing()) progressDialog.dismiss();
                        Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void dialogSignupSuccess() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Please check your email to activate your account");
        dialog.setIcon(R.drawable.check_circle);
        dialog.setTitle("Signup Success");
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }
}