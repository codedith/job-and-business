package com.xcrino.moro.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.R;

public class CreateNewGroupViewHolder extends RecyclerView.ViewHolder {

    public ImageView users_image;
    public TextView userName_title, seen_time;
    public CardView create_Cards;

    public CreateNewGroupViewHolder(@NonNull View itemView) {
        super(itemView);

        users_image = (ImageView) itemView.findViewById(R.id.users_image);
        userName_title = (TextView) itemView.findViewById(R.id.userName_title);
        seen_time = (TextView) itemView.findViewById(R.id.seen_time);
        create_Cards = (CardView) itemView.findViewById(R.id.create_Cards);

    }
}
