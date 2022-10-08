package com.example.first_app;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                findingPrimes();
            }
        });

        buttonTerminateSearchPrimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terminated = true;
            }
        });

    }

    private void findingPrimes() {
        thread = new PrimeThread();
        thread.start();
    }



    private boolean isPrime(long number) {
        boolean isPrime = false;
        for (int i = 2; i <= number / 2; ++i) {
            if (number % i == 0) {
                isPrime = true;
                break;
            }
        }

        return isPrime;
    }

    class PrimeThread extends Thread {

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            long currentTime;

            handler.post(new Runnable() {
                @Override
                public void run() {
                    textViewCurrentNumber.setText(currentNumber);
                }
            });

            while (currentNumber < Integer.MAX_VALUE - 1 && !terminated) {
                currentTime = System.currentTimeMillis();
                boolean isPrime = isPrime(currentNumber);
                if (currentTime - startTime > 2000) {
                    startTime = System.currentTimeMillis();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textViewCurrentNumber.setText(currentNumber);
                        }
                    });

                    if(isPrime) {
                        latestPrime = currentNumber;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textViewLatestPrime.setText(latestPrime);
                            }
                        });
                    }
                }

                currentNumber += INCREMENT;
            }
        }
    }
}