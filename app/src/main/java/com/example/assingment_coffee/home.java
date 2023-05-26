package com.example.assingment_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class home extends AppCompatActivity {

        ViewPager2 viewPager2;
        TextView getusername;
        BottomNavigationView bottomNavigationView;

        private Handler slideHandler = new Handler();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);

            final Button button_drink_sign = (Button) findViewById(R.id.drink_sign);
            final Button button_cold_coffee = (Button) findViewById(R.id.ice_coffee);
            final Button button_hot_coffee = (Button) findViewById(R.id.hot_coffee);
            final Button button_choco = (Button) findViewById((R.id.choco));
            final Button button_food_sign = (Button) findViewById(R.id.food_sign);
            final Button button_bakery = (Button) findViewById(R.id.bakery);
            final Button button_salad = (Button) findViewById(R.id.salad);
            final Button button_yogurt = (Button) findViewById(R.id.yogurt);
            viewPager2 = findViewById(R.id.viewPager);

            button_bakery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(),Bakery.class);
                    startActivity(i);
                }
            });

            button_salad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(),Salad.class);
                    startActivity(i);
                }
            });

            button_yogurt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(),Yogurt.class);
                    startActivity(i);
                }
            });

            button_cold_coffee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(),IceCoffee.class);
                    startActivity(i);
                }
            });

            button_hot_coffee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(),Hotcoffee.class);
                    startActivity(i);
                }
            });

            button_choco.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(),Chocolate.class);
                    startActivity(i);
                }
            });

            button_drink_sign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(),Beverages.class);
                    startActivity(i);
                }
            });

            button_food_sign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(),Food.class);
                    startActivity(i);
                }
            });
//        getusername = findViewById(R.id.getname);

            String username = getIntent().getStringExtra("keyname");
//        getusername.setText(username);
//        getusername.setText(username.toUpperCase());


            List<Silde_Model> slideItem = new ArrayList<>();
            slideItem.add(new Silde_Model(R.drawable.banner2));
            slideItem.add(new Silde_Model(R.drawable.banner3));

            viewPager2.setAdapter(new Silde_Adapter(slideItem, viewPager2));

            viewPager2.setClipToPadding(false);
            viewPager2.setClipChildren(false);
            viewPager2.setOffscreenPageLimit(5);
            viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

            CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
            compositePageTransformer.addTransformer(new MarginPageTransformer(30));
            compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                @Override
                public void transformPage(@NonNull View page, float position) {
                    float r = 1 - Math.abs(position);
                    page.setScaleY(0.85f + r * 0.15f);

                }
            });
            viewPager2.setPageTransformer(compositePageTransformer);

            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);

                    slideHandler.removeCallbacks(sliderRunable);
                    slideHandler.postDelayed(sliderRunable, 2000);
                }
            });

        }

        private Runnable sliderRunable = new Runnable() {
            @Override
            public void run() {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        };

        @Override
        public void onPause() {
            super.onPause();
            slideHandler.removeCallbacks(sliderRunable);
        }

        @Override
        public void onResume() {
            super.onResume();
            slideHandler.postDelayed(sliderRunable, 3000);
        }


}
