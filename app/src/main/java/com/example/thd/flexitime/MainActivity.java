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

    boolean isKommen = false;
    boolean isPause = false;
    boolean isGehen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DigitalClock dc = (DigitalClock) findViewById(R.id.digitalClock1);

        Button btnExit = (Button) findViewById(R.id.btn_exit);
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

        final Button btnTimeStampKommen = (Button) findViewById(R.id.btn_timestamp_kommen);

        btnTimeStampKommen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView tvTimestamp = (TextView) findViewById(R.id.tv_timestamp_kommenzeit);
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDate =  new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                String strDt = simpleDate.format(currentTime);
                tvTimestamp.setText(strDt);
                isKommen = true;
            }
        });

        final Button btnTimeStampPause = (Button) findViewById(R.id.btn_timestamp_pause);
        btnTimeStampPause.setEnabled(false);
        btnTimeStampPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
/*                if (isKommen == false) {
                    btnTimeStampPause.setEnabled(false);
                }
                else {
                    btnTimeStampPause.setEnabled(true);
                }*/

                TextView tvTimestamp = (TextView) findViewById(R.id.tv_timestamp_pausenzeit);
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDate =  new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                String strDt = simpleDate.format(currentTime);
                tvTimestamp.setText(strDt);
                isPause = true;
            }
        });

        Button btnTimeStampGehen = (Button) findViewById(R.id.btn_timestamp_gehen);
        btnTimeStampGehen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                TextView tvTimestamp = (TextView) findViewById(R.id.tv_timestamp_gehenzeit);
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDate =  new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                String strDt = simpleDate.format(currentTime);
                tvTimestamp.setText(strDt);
                isGehen = true;
            }
        });
    }
}
