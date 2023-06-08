package com.example.assingment_coffee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assingment_coffee.R;
import com.example.assingment_coffee.models.BeverageModel;
import com.example.assingment_coffee.models.FoodModel;
import com.example.assingment_coffee.models.RecyclerViewInterface;

import java.util.List;

public class FoodRelatedAdapter extends RecyclerView.Adapter<FoodRelatedAdapter.ViewHolder> {

    private List<FoodModel> foodModels;
    private Context context;
    private RecyclerViewInterface recyclerViewInterface;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productTitle, productDescription, productPrice;
        private ImageView productImage;

        public ViewHolder(View view) {
            super(view);

            productTitle = (TextView) view.findViewById(R.id.product_title);
            productDescription = (TextView) view.findViewById(R.id.product_description);
            productImage = (ImageView) view.findViewById(R.id.product_image);
            productPrice = (TextView) view.findViewById(R.id.product_price);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }

    }

    public FoodRelatedAdapter(List<FoodModel> dataSet, Context context, RecyclerViewInterface recyclerViewInterface) {
        this.foodModels = dataSet;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_related_layout, viewGroup, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.productTitle.setText(foodModels.get(position).getTitle());
        viewHolder.productDescription.setText(foodModels.get(position).getDescription());
        viewHolder.productPrice.setText(foodModels.get(position).getFormatPrice());
        Glide.with(context).load(foodModels.get(position).getImage()).into(viewHolder.productImage);
    }


    @Override
    public int getItemCount() {
        return foodModels == null ? 0 : foodModels.size();
    }

}