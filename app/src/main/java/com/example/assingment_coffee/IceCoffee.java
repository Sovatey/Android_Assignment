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

public class IceCoffee extends AppCompatActivity {

    ListView listView;
    GridView gridView;
    String[] name = {"Ice Coffee 01", "Ice Coffee 02", "Ice Coffee 03", "Ice Coffee 04","Ice Coffee 05","Ice Coffee 06"};
    int[] image = {R.drawable.ice1, R.drawable.ice2, R.drawable.ice3, R.drawable.ice4,R.drawable.ice5,R.drawable.ice6};
    private Object adapterVie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icecoffee);

        gridView = findViewById(R.id.lists_item5);

        IceCoffee.CustomAdapter customAdapter = new IceCoffee.CustomAdapter();
        gridView.setAdapter(customAdapter);
        MaterialToolbar button_back = (MaterialToolbar) findViewById(R.id.back4);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterVie, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), data_list_icecoffee.class);
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
            View view1 = getLayoutInflater().inflate(R.layout.item_list_icecoffee, null);

            TextView name_food = view1.findViewById(R.id.fruit5);
            ImageView image_food = view1.findViewById(R.id.image_view5);

            name_food.setText(name[i]);
            image_food.setImageResource(image[i]);

            return view1;
        }
    }
}
