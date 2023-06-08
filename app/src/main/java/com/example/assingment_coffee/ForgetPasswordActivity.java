package com.example.assingment_coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPasswordActivity extends AppCompatActivity {

//    EditText forgetpassword_username, forgetpassword_email, forgetpassword_password;
//    Button forgetpassword_button;
//    LoginDBHelper DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

//        forgetpassword_username = (EditText) findViewById(R.id.forgetpassword_username);
//        forgetpassword_email = (EditText) findViewById(R.id.forgetpassword_email);
//        forgetpassword_password = (EditText) findViewById(R.id.forgetpassword_password);
//        forgetpassword_button = (Button) findViewById(R.id.forgetpassword_button);
//        DB = new LoginDBHelper(this);
//
//        forgetpassword_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String username = forgetpassword_username.getText().toString();
//                String password = forgetpassword_password.getText().toString();
//                String email = forgetpassword_email.getText().toString();
//
//                if(username.equals("") || password.equals("") || email.equals(""))
//                    Toast.makeText(ForgetPasswordActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
//                else{
//                    Boolean update_password = DB.updatepassword(username,email,password);
//                    if (update_password == true){
//                        Toast.makeText(ForgetPasswordActivity.this, "Change password successfully", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                        startActivity(intent);
//                    }else{
//                        Toast.makeText(ForgetPasswordActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
    }
}