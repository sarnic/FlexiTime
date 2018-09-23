package com.example.thd.flexitime;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DigitalClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
    TimePicker picker;


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

        //Exit
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
                if (!isKommen) {
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

         btnTimeStampKommen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!isKommen) {
                    isKommen = true;
                    Calendar mcurrentTime = Calendar.getInstance();
                    int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                    int month = mcurrentTime.get(Calendar.MONTH);
                    int year = mcurrentTime.get(Calendar.YEAR);
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);

                    DatePickerDialog mDatePicker;
                    mDatePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                            if (selectedMonth < 10 || selectedDay < 10) {
                                if (selectedMonth < 10 && selectedDay >= 10) {
                                    tvTimestampDatum.setText(selectedDay + ".0" + (selectedMonth + 1) + "." + selectedYear);
                                }
                                if (selectedDay < 10 && selectedMonth >= 10) {
                                    tvTimestampDatum.setText("0" + selectedDay + "." + (selectedMonth + 1) + "." + selectedYear);
                                }
                                if (selectedMonth < 10 && selectedDay < 10) {
                                    tvTimestampDatum.setText("0" + selectedDay + ".0" + (selectedMonth + 1) + "." + selectedYear);
                                }
                            } else {
                                tvTimestampDatum.setText(selectedDay + "." + (selectedMonth + 1) + "." + selectedYear);
                            }
                        }
                    }, year, month, day);
                    mDatePicker.setTitle("Select Date");
                    mDatePicker.show();

                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            if (selectedHour < 10 || selectedMinute < 10) {
                                if (selectedHour < 10 && selectedMinute >= 10) {
                                    tvTimestampKommen.setText("0" + selectedHour + ":" + selectedMinute + ":00");
                                }
                                if (selectedMinute < 10 && selectedHour >= 10) {
                                    tvTimestampKommen.setText(selectedHour + ":" + "0" + selectedMinute + ":00");
                                }
                                if (selectedHour < 10 && selectedMinute < 10) {
                                    tvTimestampKommen.setText("0" + selectedHour + ":" + "0" + selectedMinute + ":00");
                                }
                            } else {
                                tvTimestampKommen.setText(selectedHour + ":" + selectedMinute + ":00");
                            }
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");

                    mTimePicker.show();
                    btnTimeStampPause.setEnabled(true);
                    btnTimeStampGehen.setEnabled(true);
                    btnTimeStampKommen.setEnabled(false);
                    return true;
                }
                isKommen = false;
                return false;
            }
        });

        //Pause-Stempel
        btnTimeStampPause.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {
                if (!isPauseEnde) {
                    if (isPauseAnfang) {
                        isPauseEnde = true;
                        dtCurrentTimePauseEnde = Calendar.getInstance().getTime();
                        strTime = sdfUhrzeit.format(dtCurrentTimePauseEnde);
                        tvTimestampPauseEnde.setText(strTime);
                        diffInMillies = Math.abs(dtCurrentTimePauseAnfang.getTime() - dtCurrentTimePauseEnde.getTime());

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
                if (isKommen) {
                    isGehen = true;

                    try {
                        dtCurrentTimeKommen = sdfDatumUhrzeit.parse(tvTimestampDatum.getText() + " " + tvTimestampKommen.getText());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    dtCurrentTimeGehen = Calendar.getInstance().getTime();

                    try {
                        dtCurrentTimeGehen = sdfDatumUhrzeit.parse(tvTimestampDatum.getText() + " " + sdfUhrzeit.format(dtCurrentTimeGehen));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

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

                    //Regelarbeitszeit von 8,5h pro Tag inkl. 30 Minuten Pause = 8,5*3600*1000
                    plusminusInMillies = diffInMillies - 30600000;

                    tvTimestampTotalPlusminus.setText(String.format("%02d:%02d:%02d",
                            TimeUnit.MILLISECONDS.toHours(plusminusInMillies),
                            TimeUnit.MILLISECONDS.toMinutes(Math.abs(plusminusInMillies)) -
                                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(Math.abs(plusminusInMillies))),
                            TimeUnit.MILLISECONDS.toSeconds(Math.abs(plusminusInMillies)) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(Math.abs(plusminusInMillies)))
                    ));

                    if (plusminusInMillies < 0){
                        tvTimestampTotalPlusminus.setTextColor(Color.rgb(200,0,0));
                    }
                    if (plusminusInMillies > 0){
                        tvTimestampTotalPlusminus.setTextColor(Color.rgb(0,200,0));
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
    /*
    public void read(View view) {
        try {
            FileInputStream fileInputStream= openFileInput("myText.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String lines;
            while ((lines=bufferedReader.readLine())!=null) {
                stringBuffer.append(lines+"\n");
            }
            textView.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(View view) {
        String Mytextmessage  = editText.getText().toString();
        try {
            FileOutputStream fileOutputStream = openFileOutput("myText.txt",MODE_PRIVATE);
            fileOutputStream.write(Mytextmessage.getBytes());
            fileOutputStream.close();
            Toast.makeText(getApplicationContext(),"Text Saved",Toast.LENGTH_LONG).show();
            editText.setText("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } */
}
