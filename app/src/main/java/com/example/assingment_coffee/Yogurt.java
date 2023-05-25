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

public class Yogurt extends AppCompatActivity {
    ListView listView;
    GridView gridView;
    String[] name = {"Yogurt 01", "Yogurt 02", "Yogurt 03", "Yogurt 04", "Yogurt 05","Yogurt 06"};
    int[] image = {R.drawable.yogurt1, R.drawable.yogurt2, R.drawable.yogurt3, R.drawable.yogurt4, R.drawable.yogurt5,R.drawable.yogurt6};
    private Object adapterVie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yogurt);

        gridView = findViewById(R.id.lists_item2);

        Yogurt.CustomAdapter customAdapter = new Yogurt.CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterVie, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), data_list_yogurt.class);
                intent.putExtra("name", name[i]);
                intent.putExtra("image", image[i]);
                startActivity(intent);
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
            View view1 = getLayoutInflater().inflate(R.layout.item_list_yogurt, null);

            TextView name_food = view1.findViewById(R.id.fruit2);
            ImageView image_food = view1.findViewById(R.id.image_view2);

            name_food.setText(name[i]);
            image_food.setImageResource(image[i]);

            return view1;
        }
    }
}