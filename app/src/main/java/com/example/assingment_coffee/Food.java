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

public class Food extends AppCompatActivity {
    ListView listView;
    GridView gridView;
    String[] name = {"Bagutte", "Bread", "Cookie", "Krosong","Rotiroll","Sandwich","Salad 02", "Salad 03",
                     "Salad 03", "Salad 04", "Salad 05" ,"Yogurt 01","Yogurt 02","Yogurt 03","Yogurt 04","Yogurt 05"};
    int[] image = {R.drawable.bagutte, R.drawable.bread, R.drawable.cookie, R.drawable.krosong, R.drawable.rotiroll, R.drawable.sandwich,R.drawable.salad1,
                   R.drawable.salad2, R.drawable.salad3, R.drawable.salad4, R.drawable.yogurt1,R.drawable.yogurt2, R.drawable.yogurt3, R.drawable.yogurt4,
                   R.drawable.yogurt5};
    private Object adapterVie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food);

        gridView = findViewById(R.id.lists_item7);

        Food.CustomAdapter customAdapter = new Food.CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterVie, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), data_list_food.class);
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
            View view1 = getLayoutInflater().inflate(R.layout.item_list_food, null);

            TextView name_food = view1.findViewById(R.id.fruit7);
            ImageView image_food = view1.findViewById(R.id.image_view7);

            name_food.setText(name[i]);
            image_food.setImageResource(image[i]);

            return view1;
        }
    }
}
