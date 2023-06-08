package com.example.assingment_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.assingment_coffee.adapter.FoodRelatedAdapter;
import com.example.assingment_coffee.models.FoodModel;
import com.example.assingment_coffee.models.RecyclerViewInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FoodDetailActivity extends AppCompatActivity implements RecyclerViewInterface {

    private ImageView foodDetailImage;
    private TextView viewTitle, viewPrice, viewDescription;
    private List<FoodModel> foodList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private FoodRelatedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        FoodModel foodModel = (FoodModel) getIntent().getSerializableExtra("FoodDetail");

        foodDetailImage = findViewById(R.id.food_detail_image);
        Glide.with(this).load(foodModel.getImage()).into(foodDetailImage);

        viewTitle = findViewById(R.id.food_detail_title);
        viewPrice = findViewById(R.id.food_detail_price);
        viewDescription = findViewById(R.id.food_detail_description);

        viewTitle.setText(foodModel.getTitle());
        viewPrice.setText(foodModel.getFormatPrice());
        viewDescription.setText(foodModel.getDescription());

        MaterialToolbar beverageAppBar = (MaterialToolbar) findViewById(R.id.foodDetailAppBar);
        beverageAppBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Related Product
        db.collection("food")
                .whereEqualTo("category",foodModel.getCategory())
                .whereNotEqualTo(FieldPath.documentId(), foodModel.getId())
                .limit(3)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                FoodModel foodModel = new FoodModel();
                                foodModel.setId(document.getId());
                                foodModel.setTitle(document.getString("title"));
                                foodModel.setPrice(document.getDouble("price"));
                                foodModel.setImage(document.getString("image"));
                                foodModel.setDescription(document.getString("description"));
                                foodModel.setCategory(document.getString("category"));
                                foodList.add(foodModel);
                            }

                            fetchRelatedProduct();

                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }

                    }
                });
    }

    void fetchRelatedProduct() {
        recyclerView = findViewById(R.id.food_related);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FoodRelatedAdapter(foodList, getApplicationContext(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(), FoodDetailActivity.class);
        intent.putExtra("FoodDetail", foodList.get(position));
        startActivity(intent);
    }
}