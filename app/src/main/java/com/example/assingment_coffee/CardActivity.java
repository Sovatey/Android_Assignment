package com.example.assingment_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.assingment_coffee.adapter.BeverageRelatedAdapter;
import com.example.assingment_coffee.adapter.CartAdapter;
import com.example.assingment_coffee.models.CartModel;
import com.example.assingment_coffee.models.FoodModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import java.util.List;

public class CardActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    List<CartModel> cartModelList = new ArrayList<>();

    private RecyclerView recyclerView;
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        db.collection("cart")
                .whereEqualTo("userid", currentUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(progressDialog.isShowing()) progressDialog.dismiss();

                        if(task.isSuccessful()) {
                            List<String> lst = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CartModel cartModel = new CartModel();
                                FoodModel foodModel = new FoodModel();
                                cartModel.setCartID(document.getId());
                                cartModel.setAmount(document.getDouble("amount"));
                                foodModel.setId(document.getString("productid").trim());
                                cartModel.setFood(foodModel);
                                lst.add(foodModel.getId().trim());
                                cartModelList.add(cartModel);
                            }

                            // Get beverage detail
                            db.collection("beverage")
                                    .whereIn(FieldPath.documentId(), lst)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    for (int i = 0; i < cartModelList.size(); i++) {
                                                        if (cartModelList.get(i).getFood().getId().equalsIgnoreCase(document.getId().trim())) {
                                                            cartModelList.get(i).getFood().setTitle(document.getString("title"));
                                                            cartModelList.get(i).getFood().setPrice(document.getDouble("price"));
                                                            cartModelList.get(i).getFood().setImage(document.getString("image"));
                                                            cartModelList.get(i).getFood().setDescription(document.getString("description"));
                                                            cartModelList.get(i).getFood().setCategory(document.getString("category"));
                                                            Toast.makeText(CardActivity.this, cartModelList.get(0).getFood().getTitle() + "Test1", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    });

                            // Get food detail
                            db.collection("food")
                                    .whereIn(FieldPath.documentId(), lst)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                for(int i = 0; i<cartModelList.size(); i++) {
                                                    if(cartModelList.get(i).getFood().getId().equalsIgnoreCase(document.getId())) {
                                                        cartModelList.get(i).getFood().setTitle(document.getString("title"));
                                                        cartModelList.get(i).getFood().setPrice(document.getDouble("price"));
                                                        cartModelList.get(i).getFood().setImage(document.getString("image"));
                                                        cartModelList.get(i).getFood().setDescription(document.getString("description"));
                                                        cartModelList.get(i).getFood().setCategory(document.getString("category"));
                                                    }
                                                }
                                            }

                                            recyclerView = findViewById(R.id.cart_recyclerview);
                                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                            recyclerView.setLayoutManager(layoutManager);
                                            adapter = new CartAdapter(cartModelList, getApplicationContext());
                                            recyclerView.setAdapter(adapter);
                                        }
                                    });

                        }
                    }
                });


        // Mark: Click back button
        MaterialToolbar cardAppBar = (MaterialToolbar) findViewById(R.id.cartAppBar);
        cardAppBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}