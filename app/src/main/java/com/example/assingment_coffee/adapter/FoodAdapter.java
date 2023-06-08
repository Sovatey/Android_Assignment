package com.example.assingment_coffee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.assingment_coffee.R;
import com.example.assingment_coffee.models.FoodModel;

import java.util.List;

public class FoodAdapter extends ArrayAdapter<FoodModel> {

    public FoodAdapter(@NonNull Context context, @NonNull List<FoodModel> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View customView = convertView;

        if (customView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            customView = inflater.inflate(R.layout.item_list_food, null);
        }

        TextView title = customView.findViewById(R.id.item_food_title);
        ImageView imageView = customView.findViewById(R.id.item_food_image);
        TextView description = customView.findViewById(R.id.item_food_description);
        TextView price = customView.findViewById(R.id.item_food_price);

        FoodModel foodModel = getItem(position);
        title.setText(foodModel.getTitle());
        description.setText(foodModel.getDescription());
        price.setText(foodModel.getFormatPrice());
        Glide.with(getContext()).load(foodModel.getImage()).into(imageView);

        return customView;
    }

}
