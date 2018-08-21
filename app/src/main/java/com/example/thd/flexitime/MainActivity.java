package com.example.thd.flexitime;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    boolean isKommen, isPauseAnfang, isPauseEnde, isGehen;
    long diffInMillies, plusminusInMillies;
    Button btnExit, btnTimeStampKommen, btnTimeStampPause, btnTimeStampGehen;
    DigitalClock digitalClock;
    TextView tvTimestampKommen, tvTimestampPauseAnfang, tvTimestampPauseEnde, tvTimestampPauseDifferenz, tvTimestampGehen, tvTimestampDatum, tvTimestampTotalzeitAnfang, tvTimestampTotalzeitEnde, tvTimestampTotalzeit, tvTimestampTotalPlusminus;
    Date dtCurrentTimeKommen, dtCurrentTimeGehen, dtCurrentTimePauseAnfang, dtCurrentTimePauseEnde;
    SimpleDateFormat sdfDatumUhrzeit, sdfDatum, sdfUhrzeit;
    String strDate, strTime, strDif;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVariables();

        isKommen = false;
        isPauseAnfang = false;
        isPauseEnde = false;
        isGehen = false;
        btnTimeStampPause.setEnabled(false);
        btnTimeStampGehen.setEnabled(false);
        sdfDatumUhrzeit = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        sdfDatum = new SimpleDateFormat("dd.MM.yyyy");
        sdfUhrzeit = new SimpleDateFormat("HH:mm:ss");
        digitalClock = findViewById(R.id.digitalClock1);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Exit");
                builder.setMessage("Do you want to exit?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        finish();

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //Kommen-Stempel
        btnTimeStampKommen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (isKommen == false) {
                    isKommen = true;
                    dtCurrentTimeKommen = Calendar.getInstance().getTime();
                    strTime = sdfUhrzeit.format(dtCurrentTimeKommen);
                    strDate = sdfDatum.format(dtCurrentTimeKommen);
                    tvTimestampKommen.setText(strTime);
                    tvTimestampDatum.setText(strDate);
                    tvTimestampTotalzeitAnfang.setText(strTime);
                    btnTimeStampPause.setEnabled(true);
                    btnTimeStampGehen.setEnabled(true);
                    btnTimeStampKommen.setEnabled(false);
                }
            }
        });

        //Pause-Stempel
        btnTimeStampPause.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {
                if (isPauseEnde == false) {
                    if (isPauseAnfang == true) {
                        isPauseEnde = true;
                        dtCurrentTimePauseEnde = Calendar.getInstance().getTime();
                        strTime = sdfUhrzeit.format(dtCurrentTimePauseEnde);
                        tvTimestampPauseEnde.setText(strTime);
                        diffInMillies = Math.abs(dtCurrentTimePauseEnde.getTime() - dtCurrentTimePauseAnfang.getTime());

                        tvTimestampPauseDifferenz.setText(String.format("%02d:%02d:%02d",
                                TimeUnit.MILLISECONDS.toHours(diffInMillies),
                                TimeUnit.MILLISECONDS.toMinutes(diffInMillies) -
                                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diffInMillies)),
                                TimeUnit.MILLISECONDS.toSeconds(diffInMillies) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diffInMillies))
                        ));


                        btnTimeStampPause.setEnabled(false);
                    } else {
                        isPauseAnfang = true;
                        dtCurrentTimePauseAnfang = Calendar.getInstance().getTime();
                        strTime = sdfUhrzeit.format(dtCurrentTimePauseAnfang);
                        tvTimestampPauseAnfang.setText(strTime);
                    }
                }
            }
        });

        //Gehen-Stempel
        btnTimeStampGehen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (isKommen == true) {
                    isGehen = true;
                    dtCurrentTimeGehen = Calendar.getInstance().getTime();
                    strTime = sdfUhrzeit.format(dtCurrentTimeGehen);
                    tvTimestampGehen.setText(strTime);
                    tvTimestampTotalzeitEnde.setText(strTime);

                    diffInMillies = Math.abs(dtCurrentTimeKommen.getTime() - dtCurrentTimeGehen.getTime());

                    tvTimestampTotalzeit.setText(String.format("%02d:%02d:%02d",
                            TimeUnit.MILLISECONDS.toHours(diffInMillies),
                            TimeUnit.MILLISECONDS.toMinutes(diffInMillies) -
                                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diffInMillies)),
                            TimeUnit.MILLISECONDS.toSeconds(diffInMillies) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diffInMillies))
                    ));

                    //Regelarbeitszeit von 8,5h pro Tag inkl. 30 Minuten Pause
                    plusminusInMillies = diffInMillies - 30600000;

                    tvTimestampTotalPlusminus.setText(String.format("%02d:%02d:%02d",
                            TimeUnit.MILLISECONDS.toHours(plusminusInMillies),
                            TimeUnit.MILLISECONDS.toMinutes(plusminusInMillies) -
                                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(plusminusInMillies)),
                            TimeUnit.MILLISECONDS.toSeconds(plusminusInMillies) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(plusminusInMillies))
                    ));

                    if (plusminusInMillies < 0){
                        tvTimestampTotalPlusminus.setTextColor(Color.rgb(200,0,0));
                    }


                    btnTimeStampGehen.setEnabled(false);
                }
            }
        });
    }

    private void initializeVariables() {

        tvTimestampKommen = findViewById(R.id.tv_timestamp_kommenzeit);
        tvTimestampPauseAnfang = findViewById(R.id.tv_timestamp_pausenzeit_anfang);
        tvTimestampPauseEnde = findViewById(R.id.tv_timestamp_pausenzeit_ende);
        tvTimestampPauseDifferenz = findViewById(R.id.tv_timestamp_pausenzeit_differenz);
        tvTimestampGehen = findViewById(R.id.tv_timestamp_gehenzeit);
        tvTimestampDatum = findViewById(R.id.tv_timestamp_date_day);
        tvTimestampTotalzeitAnfang = findViewById(R.id.tv_timestamp_totalzeit_anfang);
        tvTimestampTotalzeitEnde = findViewById(R.id.tv_timestamp_totalzeit_ende);
        tvTimestampTotalzeit = findViewById(R.id.tv_timestamp_totalzeit);
        tvTimestampTotalPlusminus = findViewById(R.id.tv_timestamp_total_plusminus);
        btnTimeStampKommen = findViewById(R.id.btn_timestamp_kommen);
        btnTimeStampPause = findViewById(R.id.btn_timestamp_pause);
        btnTimeStampGehen = findViewById(R.id.btn_timestamp_gehen);
        btnExit = findViewById(R.id.btn_exit);

    }
}
