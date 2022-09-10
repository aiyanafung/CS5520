package com.example.first_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyApp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.btnDoMagic);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Yazhe Feng, feng.yaz@northeastern.edu");
                Toast.makeText(getApplicationContext(), "Yazhe Feng, feng.yaz@northeastern.edu", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
