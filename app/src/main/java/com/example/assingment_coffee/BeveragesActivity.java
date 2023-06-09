package com.example.assingment_coffee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assingment_coffee.adapter.BeverageAdapter;
import com.example.assingment_coffee.models.BeverageModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BeveragesActivity extends AppCompatActivity {

    private GridView gridView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private List<BeverageModel> beverageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beverages);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        // Mark: Get category of beverage
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");

        db.collection("beverage")
            .whereEqualTo("category",category)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if(progressDialog.isShowing()) progressDialog.dismiss();

                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            BeverageModel beverageModel = new BeverageModel();
                            beverageModel.setId(document.getId());
                            beverageModel.setTitle(document.getString("title"));
                            beverageModel.setPrice(document.getDouble("price"));
                            beverageModel.setImage(document.getString("image"));
                            beverageModel.setDescription(document.getString("description"));
                            beverageModel.setCategory(document.getString("category"));
                            beverageList.add(beverageModel);
                        }
                    } else {
                        Log.w("TAG", "Error getting documents.", task.getException());
                    }

                    BeverageAdapter adapter = new BeverageAdapter(getApplicationContext(), beverageList);
                    gridView = findViewById(R.id.beverage_gridview);
                    gridView.setAdapter(adapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterVie, View view, int i, long l) {
                            Intent intent = new Intent(getApplicationContext(), BeverageDetailActivity.class);
                            intent.putExtra("BeverageDetail", beverageList.get(i));
                            startActivity(intent);
                        }
                    });
                }
            });

        MaterialToolbar beverageAppBar = (MaterialToolbar) findViewById(R.id.beverageAppBar);
        beverageAppBar.setTitle(category);
        beverageAppBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}
