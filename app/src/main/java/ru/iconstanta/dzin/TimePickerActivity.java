package ru.iconstanta.dzin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

public class TimePickerActivity extends AppCompatActivity {
    private boolean mIsActive;

    private TimePicker mTimePicker;
    private int mHour;
    private int mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        mTimePicker = (TimePicker) findViewById(R.id.time_picker);
        mTimePicker.setIs24HourView(true);
        mHour = getIntent().getExtras().getInt("Hour");
        mMinute = getIntent().getExtras().getInt("Minute");

        mTimePicker.setCurrentHour(mHour);
        mTimePicker.setCurrentMinute(mMinute);
    }

    public void onCancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onOK(View view) {
        mHour = mTimePicker.getCurrentHour();
        mMinute= mTimePicker.getCurrentMinute();
        Intent answerIntent = new Intent();
        answerIntent.putExtra("Hour", mHour);
        answerIntent.putExtra("Minute", mMinute);
        setResult(RESULT_OK, answerIntent);
        finish();
    }
}
