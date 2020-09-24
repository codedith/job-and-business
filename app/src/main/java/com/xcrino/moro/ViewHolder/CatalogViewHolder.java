package com.xcrino.moro.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.R;

public class CatalogViewHolder extends RecyclerView.ViewHolder {

    public ImageView mProduct_image;
    public TextView mProduct_title, mProduct_description, mProduct_price;

    public CatalogViewHolder(@NonNull View itemView) {
        super(itemView);

        mProduct_image = itemView.findViewById(R.id.product_image);
        mProduct_title = itemView.findViewById(R.id.product_title);
        mProduct_description = itemView.findViewById(R.id.product_description);
        mProduct_price = itemView.findViewById(R.id.product_price);
    }
}
