package com.example.first_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class Activity3 extends AppCompatActivity {
    RecyclerView linksRecyclerView;
    List<URL> linksList;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        linksList = new ArrayList<>();

        linksRecyclerView = findViewById(R.id.links_recycler_view);
        linksRecyclerView.setHasFixedSize(true);
        linksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        URLAdapter urlAdapter = new URLAdapter(linksList, this);
        linksRecyclerView.setAdapter(urlAdapter);

        EditText linkNameEditText = findViewById(R.id.link_name_input);
        EditText linkUrEditText = findViewById(R.id.link_url_input);

        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String linkName = linkNameEditText.getText().toString();
                String linkUrl = linkUrEditText.getText().toString();
                URL newUrl = new URL(linkName, linkUrl);
                linksList.add(newUrl);

                Snackbar snackbar = Snackbar.make(findViewById(R.id.activity3),"Successfully created!",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

    }
}