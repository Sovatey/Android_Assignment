package com.example.assingment_coffee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class Hotcoffee extends AppCompatActivity {
    ListView listView;
    GridView gridView;
    String[] name = {"Hot Coffee 01", "Hot Coffee 02", "Hot Coffee 03", "Hot Coffee 04"};
    int[] image = {R.drawable.coffee1, R.drawable.coffee2, R.drawable.coffee3, R.drawable.coffee4};
    private Object adapterVie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotcoffee);

        gridView = findViewById(R.id.lists_item4);

        Hotcoffee.CustomAdapter customAdapter = new Hotcoffee.CustomAdapter();
        gridView.setAdapter(customAdapter);
        MaterialToolbar button_back = (MaterialToolbar) findViewById(R.id.back3);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterVie, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), data_listhocoffee.class);
                intent.putExtra("name", name[i]);
                intent.putExtra("image", image[i]);
                startActivity(intent);
            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),home.class);
                startActivity(i);
            }
        });
    }

    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return image.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.item_list_hotcoffee, null);

            TextView name_food = view1.findViewById(R.id.fruit4);
            ImageView image_food = view1.findViewById(R.id.image_view4);

            name_food.setText(name[i]);
            image_food.setImageResource(image[i]);

            return view1;
        }
    }
}
