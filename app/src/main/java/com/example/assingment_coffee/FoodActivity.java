package com.example.assingment_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.assingment_coffee.adapter.FoodAdapter;
import com.example.assingment_coffee.models.FoodModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    private GridView gridView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private List<FoodModel> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        // Mark: Get category of beverage
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");

        db.collection("food")
                .whereEqualTo("category",category)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(progressDialog.isShowing()) progressDialog.dismiss();

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
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }

                        FoodAdapter adapter = new FoodAdapter(getApplicationContext(), foodList);
                        gridView = findViewById(R.id.food_gridview);
                        gridView.setAdapter(adapter);

                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterVie, View view, int i, long l) {
                                Intent intent = new Intent(getApplicationContext(), FoodDetailActivity.class);
                                intent.putExtra("FoodDetail", foodList.get(i));
                                startActivity(intent);
                            }
                        });
                    }
                });

        MaterialToolbar foodAppBar = (MaterialToolbar) findViewById(R.id.foodAppBar);
        foodAppBar.setTitle(category);
        foodAppBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}