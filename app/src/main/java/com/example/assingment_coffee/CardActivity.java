package com.example.assingment_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assingment_coffee.adapter.CartAdapter;
import com.example.assingment_coffee.models.CartModel;
import com.example.assingment_coffee.models.FoodModel;
import com.example.assingment_coffee.models.RecyclerViewInterface;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardActivity extends AppCompatActivity implements RecyclerViewInterface {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    List<CartModel> cartModelList = new ArrayList<>();

    TextView cartTotal, cartTax, cartPay, emptyCard;

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private View bottomLayoutCart;
    private ScrollView scrollViewCart;
    private Button cartCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        cartTotal = findViewById(R.id.total_cart_price);
        cartTax = findViewById(R.id.total_cart_tax);
        cartPay = findViewById(R.id.total_cart_paid);
        emptyCard = findViewById(R.id.emptyCard);
        bottomLayoutCart = findViewById(R.id.bottomLayoutCart);
        scrollViewCart = findViewById(R.id.scrollViewCart);
        cartCheckout = findViewById(R.id.cart_checkout);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        db.collection("cart")
                .whereEqualTo("userid", currentUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

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
                            if(cartModelList.size() > 0) {
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
                                                            }
                                                        }
                                                    }

                                                    // Get food detail
                                                    db.collection("food")
                                                            .whereIn(FieldPath.documentId(), lst)
                                                            .get()
                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                                        for (int i = 0; i < cartModelList.size(); i++) {
                                                                            if (cartModelList.get(i).getFood().getId().equalsIgnoreCase(document.getId())) {
                                                                                cartModelList.get(i).getFood().setTitle(document.getString("title"));
                                                                                cartModelList.get(i).getFood().setPrice(document.getDouble("price"));
                                                                                cartModelList.get(i).getFood().setImage(document.getString("image"));
                                                                                cartModelList.get(i).getFood().setDescription(document.getString("description"));
                                                                                cartModelList.get(i).getFood().setCategory(document.getString("category"));
                                                                            }
                                                                        }
                                                                    }

                                                                    if (progressDialog.isShowing())
                                                                        progressDialog.dismiss();
                                                                    recyclerView = findViewById(R.id.cart_recyclerview);
                                                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                                                    recyclerView.setLayoutManager(layoutManager);
                                                                    adapter = new CartAdapter(cartModelList, getApplicationContext(), CardActivity.this);
                                                                    recyclerView.setAdapter(adapter);
                                                                    calculateCart();
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    if (progressDialog.isShowing())
                                                                        progressDialog.dismiss();
                                                                }
                                                            });
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                if (progressDialog.isShowing()) progressDialog.dismiss();
                                            }
                                        });
                            } else {
                                calculateCart();
                                if (progressDialog.isShowing()) progressDialog.dismiss();
                            }
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

        // Mark: Checkout
        cartCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Processing...");
                progressDialog.show();
                for(CartModel cart : cartModelList) {
                    db.collection("cart").document(cart.getCartID()).delete();
                }
                progressDialog.dismiss();
                cartModelList.clear();

                new AlertDialog.Builder(CardActivity.this)
                        .setTitle("Checkout Success")
                        .setMessage("You have been checkout successfully")
                        .setIcon(R.drawable.check_circle)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                calculateCart();
                            }
                        })
                        .show();
            }
        });

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onCartAmountChanged(int position, double amount) {
        if(amount >= 1) {
            cartModelList.get(position).setAmount(amount);
            db.collection("cart").document(cartModelList.get(position).getCartID()).update("amount",amount);
        } else {
            db.collection("cart").document(cartModelList.get(position).getCartID()).delete();
        }
        calculateCart();
    }

    private void calculateCart() {
        double amount = 0.0;
        for(CartModel cart : cartModelList) {
            amount += cart.getFood().getPrice() * cart.getAmount();
        }

        double tax = amount * 10 / 100;

        cartTotal.setText(new DecimalFormat("$ 0.00").format(amount));
        cartTax.setText(new DecimalFormat("$ 0.00").format(tax));
        cartPay.setText(new DecimalFormat("$ 0.00").format(tax + amount));

        if(cartModelList.size() > 0) {
            emptyCard.setVisibility(View.GONE);
            scrollViewCart.setVisibility(View.VISIBLE);
            bottomLayoutCart.setVisibility(View.VISIBLE);
        } else {
            emptyCard.setVisibility(View.VISIBLE);
            scrollViewCart.setVisibility(View.GONE);
            bottomLayoutCart.setVisibility(View.GONE);
        }

    }
}