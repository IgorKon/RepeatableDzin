package ru.iconstanta.dzin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.Context;
import android.view.View;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;
import android.text.format.Time;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "dzin_settings";
    public static final String APP_PREFERENCES_IsActive = "IsActive";
    public static final String APP_PREFERENCES_StartHour = "StartHour";
    public static final String APP_PREFERENCES_StartMinute = "StartMinute";
    public static final String APP_PREFERENCES_StopHour = "StopHour";
    public static final String APP_PREFERENCES_StopMinute = "StopMinute";
    public static final String APP_PREFERENCES_PeriodHour = "PeriodHour";
    public static final String APP_PREFERENCES_PeriodMinute = "PeriodMinute";
    private static final int START_TIME_PICKER = 0;

    private boolean mIsActive;

    private Switch mActiveSwitch;
    private TextView mStartTextLabel;
    private TextView mStartTextClock;
    private TextView mStopTextLabel;
    private TextView mStopTextClock;
    private TextView mPeriodTextLabel;
    private TextView mPeriodTextClock;

    private Time mStartTime;
    private Time mStopTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        mActiveSwitch = (Switch) findViewById(R.id.ActiveSwitch);
        mStartTextLabel = (TextView) findViewById(R.id.StartTextLabel);
        mStartTextClock = (TextView) findViewById(R.id.StartTextValue);
        mStopTextLabel = (TextView) findViewById(R.id.StopTextLabel);
        mStopTextClock = (TextView) findViewById(R.id.StopTextValue);
        mPeriodTextLabel = (TextView) findViewById(R.id.PeriodTextLabel);
        mPeriodTextClock = (TextView) findViewById(R.id.PeriodTextValue);
        mStartTime = new Time();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSettings.contains(APP_PREFERENCES_IsActive)) {
            // Получаем число из настроек
            mIsActive = mSettings.getBoolean(APP_PREFERENCES_IsActive, true);

            mStartTime.hour = mSettings.getInt(APP_PREFERENCES_StartHour, 13);
            mStartTime.minute = mSettings.getInt(APP_PREFERENCES_StartMinute, 0);
            // Выводим на экран данные из настроек
            mActiveSwitch.setChecked(mIsActive);
            mStartTextClock.setText(mStartTime.format("%H:%M"));
            SetEnabled();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean(APP_PREFERENCES_IsActive, mIsActive);
        editor.putInt(APP_PREFERENCES_StartHour, mStartTime.hour);
        editor.putInt(APP_PREFERENCES_StartMinute, mStartTime.minute);
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == START_TIME_PICKER) {
            if (resultCode == RESULT_OK) {
                mStartTime.hour = data.getIntExtra("Hour", 13);
                mStartTime.minute = data.getIntExtra("Minute", 0);
                mStartTextClock.setText(mStartTime.format("%H:%M"));
            }
        }
    }

    public void onActiveChanged(View view) {
        mIsActive = mActiveSwitch.isChecked();
        SetEnabled();
    }

    public void onStartClick(View view) {
        Intent intent = new Intent(MainActivity.this, TimePickerActivity.class);
        intent.putExtra("Hour", mStartTime.hour);
        intent.putExtra("Minute", mStartTime.minute);
        startActivityForResult(intent, START_TIME_PICKER);
    }

    protected void SetEnabled() {
        mStartTextLabel.setEnabled(mIsActive);
        mStartTextClock.setEnabled(mIsActive);
        mStopTextLabel.setEnabled(mIsActive);
        mStopTextClock.setEnabled(mIsActive);
        mPeriodTextLabel.setEnabled(mIsActive);
        mPeriodTextClock.setEnabled(mIsActive);
    }
}
