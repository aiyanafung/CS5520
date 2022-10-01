package com.example.first_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class URLAdapter extends RecyclerView.Adapter<URLViewHolder>{
    private List<URL> links;
    private Context context;

    //Creates a URLAdapter with the provided arraylist of URL objects.
    public URLAdapter(List<URL> links, Context context) {
        this.links = links;
        this.context = context;
    }

    @NonNull
    @Override
    public URLViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new URLViewHolder(LayoutInflater.from(context).inflate(R.layout.item_links, null));
    }

    @Override
    public void onBindViewHolder(@NonNull URLViewHolder holder, int position) {
        holder.name.setText(links.get(position).getName());
        holder.url.setText(links.get(position).getUrl());
        holder.itemView.setOnClickListener(view -> {
            String url = links.get(position).getUrl();
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url;
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(browserIntent);
        });

    }

    @Override
    public int getItemCount() {
        return links.size();
    }
}
