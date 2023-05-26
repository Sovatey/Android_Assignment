package com.example.assingment_coffee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class data_list_beverages extends AppCompatActivity {
    TextView name;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list_beverages);
        name = findViewById(R.id.fruit6);
        image = findViewById(R.id.image_view6);

        Intent intent = getIntent();

        name.setText(intent.getStringExtra("name"));
        image.setImageResource(intent.getIntExtra("image",0));

        MaterialToolbar button_back = (MaterialToolbar) findViewById(R.id.back8);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Beverages.class);
                startActivity(i);
            }
        });
    }
}
