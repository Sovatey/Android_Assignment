package com.example.assingment_coffee.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assingment_coffee.R;
import com.example.assingment_coffee.models.CartModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<CartModel> cartModelList;
    private Context context;

    public CartAdapter(List<CartModel> dataSet, Context context) {
        this.cartModelList = dataSet;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView cartTitle, cartDescription, cartPrice, cartAmount;
        private ImageView cartImage;

        public ViewHolder(@NonNull View view) {
            super(view);

            cartTitle = view.findViewById(R.id.cart_title);
            cartDescription = view.findViewById(R.id.cart_description);
            cartPrice = view.findViewById(R.id.cart_price);
            cartImage = view.findViewById(R.id.cart_image);
            cartAmount = view.findViewById(R.id.cart_amount);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_cart, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.cartTitle.setText(cartModelList.get(position).getFood().getTitle());
        viewHolder.cartDescription.setText(cartModelList.get(position).getFood().getDescription());
        viewHolder.cartPrice.setText(cartModelList.get(position).getFood().getFormatPrice());
        Glide.with(context).load(cartModelList.get(position).getFood().getImage()).into(viewHolder.cartImage);
    }

    @Override
    public int getItemCount() {
        return cartModelList == null ? 0 : cartModelList.size();
    }

}
