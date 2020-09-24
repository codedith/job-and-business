package com.xcrino.moro.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.R;
import com.xcrino.moro.ViewHolder.CatalogViewHolder;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogViewHolder> {

    Context mContext;

    public CatalogAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public CatalogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_catalog, parent, false);
        CatalogViewHolder catalogViewHolder = new CatalogViewHolder(view);
        return catalogViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
