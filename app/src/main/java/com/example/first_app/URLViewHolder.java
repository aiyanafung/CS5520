package com.example.first_app;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class URLViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView url;

    public URLViewHolder(@NonNull View itemView) {
        super(itemView);
        this.url = itemView.findViewById(R.id.link_item_url);
        this.name = itemView.findViewById(R.id.link_item_name);
    }
}
