package com.example.pacak;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView[] tablica;
    Button button;
    TextView czas;
    TextView punkty;
    CountDownTimer countDownTimer;
    Random random = new Random();



    Handler handler = new Handler();

    int punktyValue = 0;
    boolean graTrwa = false;
    ImageView aktualneJablko = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        czas = findViewById(R.id.textView2);
        punkty = findViewById(R.id.textView4);

        tablica = new ImageView[9];
        tablica[0] = findViewById(R.id.imageView);
        tablica[1] = findViewById(R.id.imageView2);
        tablica[2] = findViewById(R.id.imageView3);
        tablica[3] = findViewById(R.id.imageView4);
        tablica[4] = findViewById(R.id.imageView5);
        tablica[5] = findViewById(R.id.imageView6);
        tablica[6] = findViewById(R.id.imageView7);
        tablica[7] = findViewById(R.id.imageView8);
        tablica[8] = findViewById(R.id.imageView9);


        for (ImageView img : tablica) {
            img.setOnClickListener(v -> {
                if (graTrwa && v == aktualneJablko) {
                    punktyValue++;
                    punkty.setText(String.valueOf(punktyValue));

                    aktualneJablko.setVisibility(ImageView.INVISIBLE);
                    aktualneJablko = null;
                }
            });
        }

        button.setOnClickListener(v -> startGry());
    }

    void startGry() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        handler.removeCallbacksAndMessages(null);

        punktyValue = 0;
        punkty.setText("0");
        graTrwa = true;

        for (ImageView img : tablica) {
            img.setVisibility(ImageView.INVISIBLE);
        }

        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                czas.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                graTrwa = false;
                czas.setText("0");

                if (aktualneJablko != null) {
                    aktualneJablko.setVisibility(ImageView.INVISIBLE);
                    aktualneJablko = null;
                }
            }
        }.start();

        spawnJablko();
    }

    void spawnJablko() {
        if (!graTrwa) return;

        int index = random.nextInt(9);

        aktualneJablko = tablica[index];
        aktualneJablko.setVisibility(ImageView.VISIBLE);

        handler.postDelayed(() -> {
            if (aktualneJablko != null) {
                aktualneJablko.setVisibility(ImageView.INVISIBLE);
                aktualneJablko = null;
            }

            spawnJablko();

        }, 500);
    }
}