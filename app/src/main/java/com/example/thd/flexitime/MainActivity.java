package com.example.thd.flexitime;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    boolean isKommen, isPauseAnfang, isPauseEnde, isGehen;
    Button btnExit, btnTimeStampKommen, btnTimeStampPause, btnTimeStampGehen;
    DigitalClock digitalClock;
    TextView tvTimestampKommen, tvTimestampPauseAnfang, tvTimestampPauseEnde, tvTimestampPauseDifferenz, tvTimestampGehen, tvTimestampDatum;
    Date currentTime;
    SimpleDateFormat sdfDatumUhrzeit, sdfDatum, sdfUhrzeit;
    String strDate, strTime;


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
        sdfDatumUhrzeit =  new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        sdfDatum = new SimpleDateFormat("dd.MM.yyyy");
        sdfUhrzeit =  new SimpleDateFormat("HH:mm:ss");
        digitalClock = (DigitalClock) findViewById(R.id.digitalClock1);

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
                if (isKommen == false){
                    isKommen = true;
                    currentTime = Calendar.getInstance().getTime();
                    strTime = sdfUhrzeit.format(currentTime);
                    strDate = sdfDatum.format(currentTime);
                    tvTimestampKommen.setText(strTime);
                    tvTimestampDatum.setText(strDate);
                    btnTimeStampPause.setEnabled(true);
                    btnTimeStampGehen.setEnabled(true);
                    btnTimeStampKommen.setEnabled(false);
                }
            }
        });

        //Pause-Stempel
        btnTimeStampPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (isPauseEnde == false) {
                    if (isPauseAnfang == true){
                        isPauseEnde = true;
                        currentTime = Calendar.getInstance().getTime();
                        strTime = sdfUhrzeit.format(currentTime);
                        tvTimestampPauseEnde.setText(strTime);
                        btnTimeStampPause.setEnabled(false);
                    }
                    else {
                        isPauseAnfang = true;
                        currentTime = Calendar.getInstance().getTime();
                        strTime = sdfUhrzeit.format(currentTime);
                        tvTimestampPauseAnfang.setText(strTime);
                    }
                }
            }
        });

        //Gehen-Stempel
        btnTimeStampGehen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (isKommen == true){
                    isGehen = true;
                    currentTime = Calendar.getInstance().getTime();
                    strTime = sdfUhrzeit.format(currentTime);
                    tvTimestampGehen.setText(strTime);
                    btnTimeStampGehen.setEnabled(false);
                }
            }
        });
    }

    private void initializeVariables(){

        tvTimestampKommen = (TextView) findViewById(R.id.tv_timestamp_kommenzeit);
        tvTimestampPauseAnfang = (TextView) findViewById(R.id.tv_timestamp_pausenzeit_anfang);
        tvTimestampPauseEnde = (TextView) findViewById(R.id.tv_timestamp_pausenzeit_ende);
        tvTimestampPauseDifferenz = (TextView) findViewById(R.id.tv_timestamp_pausenzeit_differenz);
        tvTimestampGehen = (TextView) findViewById(R.id.tv_timestamp_gehenzeit);
        tvTimestampDatum = (TextView) findViewById(R.id.tv_timestamp_date_day);
        btnTimeStampKommen = (Button) findViewById(R.id.btn_timestamp_kommen);
        btnTimeStampPause = (Button) findViewById(R.id.btn_timestamp_pause);
        btnTimeStampGehen = (Button) findViewById(R.id.btn_timestamp_gehen);
        btnExit = (Button) findViewById(R.id.btn_exit);

    }
}
