package com.example.assingment_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.assingment_coffee.adapter.BeverageRelatedAdapter;
import com.example.assingment_coffee.models.FoodModel;
import com.example.assingment_coffee.models.RecyclerViewInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeverageDetailActivity extends AppCompatActivity implements RecyclerViewInterface {

    private ImageView beverageDetailImage;
    private TextView viewTitle, viewPrice, viewDescription;
    private List<FoodModel> beverageList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private BeverageRelatedAdapter adapter;
    private Button btnAddToCart;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beverage_detail);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        FoodModel beverageModel = (FoodModel) getIntent().getSerializableExtra("BeverageDetail");

        beverageDetailImage = findViewById(R.id.beverage_detail_image);
        Glide.with(this).load(beverageModel.getImage()).into(beverageDetailImage);

        viewTitle = findViewById(R.id.beverage_detail_title);
        viewPrice = findViewById(R.id.beverage_detail_price);
        viewDescription = findViewById(R.id.beverage_detail_description);
        btnAddToCart = findViewById(R.id.beverage_add_card);

        viewTitle.setText(beverageModel.getTitle());
        viewPrice.setText(beverageModel.getFormatPrice());
        viewDescription.setText(beverageModel.getDescription());

        MaterialToolbar beverageAppBar = (MaterialToolbar) findViewById(R.id.beverageDetailAppBar);
        beverageAppBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Related Product
        db.collection("beverage")
                .whereEqualTo("category",beverageModel.getCategory())
                .whereNotEqualTo(FieldPath.documentId(), beverageModel.getId())
                .limit(3)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                FoodModel beverageModel = new FoodModel();
                                beverageModel.setId(document.getId());
                                beverageModel.setTitle(document.getString("title"));
                                beverageModel.setPrice(document.getDouble("price"));
                                beverageModel.setImage(document.getString("image"));
                                beverageModel.setDescription(document.getString("description"));
                                beverageModel.setCategory(document.getString("category"));
                                beverageList.add(beverageModel);
                            }

                            fetchRelatedProduct();

                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }

                    }
                });


        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("cart")
                        .whereEqualTo("userid", currentUser.getUid())
                        .whereEqualTo("productid", beverageModel.getId())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        document.getReference().delete();
                                    }

                                    // Mark: Add to cart
                                    Map<String, Object> cart = new HashMap<>();
                                    cart.put("userid", currentUser.getUid());
                                    cart.put("productid", beverageModel.getId());
                                    cart.put("amount", 1);
                                    db.collection("cart").document()
                                            .set(cart)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(BeverageDetailActivity.this, beverageModel.getTitle() + " has been added to cart", Toast.LENGTH_LONG).show();
                                                    finish();
                                                }
                                            });


                                }
                            }
                        });
            }
        });
    }

    void fetchRelatedProduct() {
        recyclerView = findViewById(R.id.beverage_related);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BeverageRelatedAdapter(beverageList, getApplicationContext(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(), BeverageDetailActivity.class);
        intent.putExtra("BeverageDetail", beverageList.get(position));
        startActivity(intent);
    }

    @Override
    public void onCartAmountChanged(int position, double amount) {

    }
}