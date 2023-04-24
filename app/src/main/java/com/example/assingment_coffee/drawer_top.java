package com.example.assingment_coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class drawer_top extends AppCompatActivity {

    TextView getusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_top);

        getusername = findViewById(R.id.txtusername);

        String username = getIntent().getStringExtra("keyname");
        getusername.setText(username);
        getusername.setText(username.toUpperCase());
        getusername.setText("hello");
    }
}