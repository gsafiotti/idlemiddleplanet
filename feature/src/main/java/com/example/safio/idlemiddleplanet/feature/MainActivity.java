package com.example.safio.idlemiddleplanet.feature;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int energy = 0;
    int centraleElettrica = 0;
    int costoCentraleElettrica = 20;
    int produzioneCentraleElettrica = 5;




    public int mInterval = 5000; // 5 seconds by default, can be changed later
    public Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView imgPlanet = (ImageView) findViewById(R.id.planet_IV);

        Animation rotation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);
        rotation.setFillAfter(true);
        imgPlanet.startAnimation(rotation);

        final TextView energyCount = (TextView) findViewById(R.id.energyCount_TV);
        final TextView centraleElettricaTW = (TextView) findViewById(R.id.centraleElettrica_TW);
        TextView textViewTimerTW = (TextView) findViewById(R.id.textViewTimer);

        imgPlanet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                energy++;
                CharSequence testo = "Energia: " + energy;
                energyCount.setText(testo);
            }
        });

        energyCount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CharSequence testo = "Energia: " + energy;
                energyCount.setText(testo);
            }
        });


        centraleElettricaTW.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (energy>=costoCentraleElettrica){
                energy-=costoCentraleElettrica;
                centraleElettrica++;

                CharSequence toastAcquisto = "Hai comprato 1 Centrale elettrica in cambio di " + costoCentraleElettrica +
                            "unità di energia";

                costoCentraleElettrica+=3;

                Context context = getApplicationContext();
                CharSequence testo = "Energia: " + energy;
                CharSequence testoCentraleElettrica = "" + centraleElettrica + " - Centrale elettrica [Costo = " +
                        costoCentraleElettrica + "] - Compra";


                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context,toastAcquisto,duration);
                toast.show();
                energyCount.setText(testo);
                centraleElettricaTW.setText(testoCentraleElettrica);

                if (centraleElettrica==1){
                        /*
                        CountDownTimer Count = new CountDownTimer(10000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                int seconds = (int) ((millisUntilFinished / 500));

                                textViewTimerTW.setText(seconds + "seconds " + millisUntilFinished / 500);

                            }

                            public void onFinish() {
                                textViewTimerTW.setText("generata " + produzioneCentraleElettrica + " energia");
                                energy += produzioneCentraleElettrica;
                                CharSequence testo = "Energia: " + energy;
                                energyCount.setText(testo);

                            }
                        };

                        Count.start();
                        */
                        mHandler = new Handler();
                        startRepeatingTask();

                }


                }
                else{
                    Context context = getApplicationContext();
                    CharSequence testo = "Non hai abbastanza energia per comprare questo bene";

                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context,testo,duration);
                    toast.show();
                }

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                energy = produciRisorse(produzioneCentraleElettrica,centraleElettrica);
                //updateStatus(); //this function can change value of mInterval.
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();

    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    int produciRisorse(int produzione, int numeroCentraliCheProducono){
       int totale = produzione * numeroCentraliCheProducono;
        CharSequence testo = "Energia: " + energy;
        final TextView energyCount = (TextView) findViewById(R.id.energyCount_TV);
        energyCount.setText(testo);
        return energy += totale;
    }
}
