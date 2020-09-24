package com.xcrino.moro.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.R;
import com.xcrino.moro.ViewHolder.CreateNewGroupViewHolder;

public class CreateNewGroupAdapter extends RecyclerView.Adapter<CreateNewGroupViewHolder> {

    private Context mContext;

    public CreateNewGroupAdapter(Context mContext){
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public CreateNewGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.newgroup_contact, parent, false);
        CreateNewGroupViewHolder createNewGroupViewHolder = new CreateNewGroupViewHolder(view);
        return createNewGroupViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CreateNewGroupViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
