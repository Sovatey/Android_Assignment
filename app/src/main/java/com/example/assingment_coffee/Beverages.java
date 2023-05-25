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

public class Beverages extends AppCompatActivity {

    ListView listView;
    GridView gridView;
    String[] name = {"Coffee 02", "Coffee 03", "Chocolate 02", "Chocolate 03", "Chocolate 07","Chocolate 04","Ice Coffee 02","Ice Coffee 03","Ice Coffee 05"};
    int[] image = {R.drawable.coffee2, R.drawable.coffee3, R.drawable.chocolate2, R.drawable.chocolate3, R.drawable.chocolate7,R.drawable.chocolate4,R.drawable.ice2,R.drawable.ice3,R.drawable.ice5};
    private Object adapterVie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bakery);

        gridView = findViewById(R.id.lists_item);

        Beverages.CustomAdapter customAdapter = new Beverages.CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterVie, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), data_list_beverages.class);
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
            View view1 = getLayoutInflater().inflate(R.layout.item_list_beverages, null);

            TextView name_food = view1.findViewById(R.id.fruit6);
            ImageView image_food = view1.findViewById(R.id.image_view6);

            name_food.setText(name[i]);
            image_food.setImageResource(image[i]);

            return view1;
        }
    }
}
