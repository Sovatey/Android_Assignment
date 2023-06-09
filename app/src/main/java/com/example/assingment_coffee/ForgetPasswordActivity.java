package com.example.assingment_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText txtEmail;
    Button btnForgetPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        progressDialog = new ProgressDialog(this);

        txtEmail = (EditText) findViewById(R.id.forgetpassword_email);
        btnForgetPassword = (Button) findViewById(R.id.forgetpassword_button);

        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                String email = txtEmail.getText().toString();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                new AlertDialog.Builder(ForgetPasswordActivity.this)
                                        .setTitle("Reset Success")
                                        .setMessage("Please check your email to reset your account")
                                        .setIcon(R.drawable.check_circle)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(progressDialog.isShowing()) progressDialog.dismiss();
                                                finish();
                                            }
                                        })
                                        .show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if(progressDialog.isShowing()) progressDialog.dismiss();

                                new AlertDialog.Builder(ForgetPasswordActivity.this)
                                        .setTitle("Login Failed")
                                        .setMessage(e.getMessage())
                                        .setIcon(R.drawable.cancel)
                                        .setPositiveButton("OK", null)
                                        .show();
                            }
                        });
            }
        });
    }
}