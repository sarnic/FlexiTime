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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogOut = (Button) findViewById(R.id.btn_logout);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
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

        Button btnTimeStamp = (Button) findViewById(R.id.btn_timestamp);
        btnTimeStamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Zeitstempel");
                builder.setMessage("Zeitstempel wird gesetzt?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        TextView tvTimestamp = (TextView) findViewById(R.id.tv_timestamp);
                        Date currentTime = Calendar.getInstance().getTime();
                        SimpleDateFormat simpleDate =  new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                        String strDt = simpleDate.format(currentTime);
                        tvTimestamp.setText(strDt);

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        DigitalClock dc = (DigitalClock) findViewById(R.id.digitalClock1);

    }

    /**
     * Called when the user touches the button
     */
    public void sendMessage(View view) {
        // Do something in response to button click

    }
}
