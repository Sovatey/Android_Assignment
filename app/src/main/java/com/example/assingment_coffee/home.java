package com.example.assingment_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
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

            viewPager2 = findViewById(R.id.viewPager);
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
