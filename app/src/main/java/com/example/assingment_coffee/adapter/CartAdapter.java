package com.example.assingment_coffee.adapter;

import android.annotation.SuppressLint;
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
import com.example.assingment_coffee.models.RecyclerViewInterface;
import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<CartModel> cartModelList;
    private Context context;
    private RecyclerViewInterface recyclerViewInterface;

    public CartAdapter(List<CartModel> dataSet, Context context, RecyclerViewInterface recyclerViewInterface) {
        this.cartModelList = dataSet;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView cartTitle, cartDescription, cartPrice, cartAmount;
        private ImageView cartImage;
        MaterialButton btnIncrease, btnDecrease;

        public ViewHolder(@NonNull View view) {
            super(view);

            cartTitle = view.findViewById(R.id.cart_title);
            cartDescription = view.findViewById(R.id.cart_description);
            cartPrice = view.findViewById(R.id.cart_price);
            cartImage = view.findViewById(R.id.cart_image);
            cartAmount = view.findViewById(R.id.cart_amount);
            btnIncrease = view.findViewById(R.id.increase_cart_amount);
            btnDecrease = view.findViewById(R.id.decrease_card_amount);

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
        viewHolder.cartAmount.setText(new DecimalFormat("0").format(cartModelList.get(position).getAmount()));
        Glide.with(context).load(cartModelList.get(position).getFood().getImage()).into(viewHolder.cartImage);

        viewHolder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble(viewHolder.cartAmount.getText().toString()) + 1;
                viewHolder.cartAmount.setText(new DecimalFormat("0").format(amount));
                recyclerViewInterface.onCartAmountChanged(viewHolder.getAdapterPosition(), amount);
            }
        });

        viewHolder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble(viewHolder.cartAmount.getText().toString()) - 1;
                recyclerViewInterface.onCartAmountChanged(viewHolder.getAdapterPosition(), amount);

                if(amount >= 1) {
                    viewHolder.cartAmount.setText(new DecimalFormat("0").format(amount));
                } else {
                    cartModelList.remove(viewHolder.getAdapterPosition());
                    notifyItemRemoved(viewHolder.getAdapterPosition());
                    notifyItemRangeChanged(viewHolder.getAdapterPosition(), cartModelList.size());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartModelList == null ? 0 : cartModelList.size();
    }

}
