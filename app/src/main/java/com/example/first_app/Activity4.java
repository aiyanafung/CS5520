package com.example.first_app;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class Activity4 extends AppCompatActivity {
    private static final int START_NUMBER = 3;
    private static final int INCREMENT = 2;

    private TextView textViewLatestPrime;
    private TextView textViewCurrentNumber;

    private Button buttonFindPrimes;
    private Button buttonTerminateSearchPrimes;

    private int currentNumber = START_NUMBER;
    private int latestPrime;

    private boolean terminated = false;

    private Handler handler = new Handler();

    PrimeThread thread;

    private long onBackPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_4);

        textViewLatestPrime = findViewById(R.id.activity4_latest_prime);
        textViewCurrentNumber = findViewById(R.id.activity4_current_number);

        buttonFindPrimes = findViewById(R.id.button_find_primes);
        buttonTerminateSearchPrimes = findViewById(R.id.button_terminate_find_primes);


        buttonFindPrimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terminated = false;
                currentNumber = START_NUMBER;
                findingPrimes();
            }
        });

        buttonTerminateSearchPrimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terminated = true;

                textViewCurrentNumber.setText("");
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (onBackPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        onBackPressedTime = System.currentTimeMillis();
    }

    private void findingPrimes() {
        thread = new PrimeThread();
        thread.start();
    }

    private boolean isPrime(int number) {
        // Corner cases
        if (number <= 1)
            return false;
        if (number <= 3)
            return true;

        // This is checked so that we can skip
        // middle five numbers in below loop
        if (number % 2 == 0 || number % 3 == 0)
            return false;

        for (int i = 5; i * i <= number; i = i + 6)
            if (number % i == 0 || number % (i + 2) == 0)
                return false;

        return true;
    }

    class PrimeThread extends Thread {

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            long currentTime;

            handler.post(new Runnable() {
                @Override
                public void run() {
                    textViewCurrentNumber.setText(String.valueOf(currentNumber));
                }
            });

            while (currentNumber < Integer.MAX_VALUE - 1 && !terminated) {
                currentTime = System.currentTimeMillis();
                if(isPrime(currentNumber)) {
                    Log.d("ACTIVITY_4", "isPrime: " + currentNumber);
                    latestPrime = currentNumber;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textViewLatestPrime.setText(String.valueOf(latestPrime));
                        }
                    });
                }

                if (currentTime - startTime > 1000) {
                    startTime = System.currentTimeMillis();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textViewCurrentNumber.setText(String.valueOf(currentNumber));
                        }
                    });

                }

                currentNumber += INCREMENT;
            }
        }
    }
}