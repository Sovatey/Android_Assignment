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
import com.example.assingment_coffee.models.BeverageModel;

import java.util.List;

public class BeverageAdapter extends ArrayAdapter<BeverageModel> {

    public BeverageAdapter(@NonNull Context context, @NonNull List<BeverageModel> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View customView = convertView;

        if (customView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            customView = inflater.inflate(R.layout.item_list_beverages, null);
        }

        TextView title = customView.findViewById(R.id.item_beverage_title);
        ImageView imageView = customView.findViewById(R.id.item_beverage_image);
        TextView description = customView.findViewById(R.id.item_beverage_description);
        TextView price = customView.findViewById(R.id.item_beverage_price);

        BeverageModel beverageModel = getItem(position);
        title.setText(beverageModel.getTitle());
        description.setText(beverageModel.getDescription());
        price.setText(beverageModel.getFormatPrice());
        Glide.with(getContext()).load(beverageModel.getImage()).into(imageView);

        return customView;
    }

}
