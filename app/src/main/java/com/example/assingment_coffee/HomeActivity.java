package com.example.assingment_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ViewPager2 viewPager2;
    TextView getusername;
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private Handler slideHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        final Button button_drink_sign = (Button) findViewById(R.id.drink_sign);
        final Button button_cold_coffee = (Button) findViewById(R.id.ice_coffee);
        final Button button_hot_coffee = (Button) findViewById(R.id.hot_coffee);
        final Button button_choco = (Button) findViewById((R.id.choco));
        final Button button_food_sign = (Button) findViewById(R.id.food_sign);
        final Button button_bakery = (Button) findViewById(R.id.bakery);
        final Button button_salad = (Button) findViewById(R.id.salad);
        final Button button_yogurt = (Button) findViewById(R.id.yogurt);
        viewPager2 = findViewById(R.id.viewPager);

        button_food_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodActivity.class);
                intent.putExtra("category", "Signature");
                startActivity(intent);
            }
        });

        button_bakery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodActivity.class);
                intent.putExtra("category", "Bakery");
                startActivity(intent);
            }
        });

        button_salad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodActivity.class);
                intent.putExtra("category", "Salad");
                startActivity(intent);
            }
        });

        button_yogurt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodActivity.class);
                intent.putExtra("category", "Yogurt");
                startActivity(intent);
            }
        });

        button_cold_coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BeveragesActivity.class);
                intent.putExtra("category", "Iced Coffee");
                startActivity(intent);
            }
        });

        button_hot_coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BeveragesActivity.class);
                intent.putExtra("category", "Hot Coffee");
                startActivity(intent);
            }
        });

        button_choco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BeveragesActivity.class);
                intent.putExtra("category", "Chocolate");
                startActivity(intent);
            }
        });

        button_drink_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BeveragesActivity.class);
                intent.putExtra("category", "Signature");
                startActivity(intent);
            }
        });

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

        // Mark: Open drawer when click
        View topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                dLayout.openDrawer(GravityCompat.START);
            }
        });
        topAppBar.findViewById(R.id.cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardActivity.class);
                startActivity(intent);
            }
        });

        // Mark: Drawer menu item selected
        NavigationView navView = findViewById(R.id.navigationView);
        navView.bringToFront();
        navView.setNavigationItemSelectedListener(menuItem -> {
            // Mark: logout event
            if(menuItem.getItemId() == R.id.nav_logout) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                this.finish();
            }

            return false;
        });

        // Mark: Set detail to drawer header
        View drawerHeader = navView.getHeaderView(0);
        TextView profileName = drawerHeader.findViewById(R.id.profile_name);
        profileName.setText(user.getDisplayName());
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
