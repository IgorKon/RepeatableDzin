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
    public static final String APP_Hour = "Hour";
    public static final String APP_Minute = "Minute";
    public static final String APP_PREFERENCES_StartHour = "StartHour";
    public static final String APP_PREFERENCES_StartMinute = "StartMinute";
    public static final String APP_PREFERENCES_StopHour = "StopHour";
    public static final String APP_PREFERENCES_StopMinute = "StopMinute";
    public static final String APP_PREFERENCES_PeriodHour = "PeriodHour";
    public static final String APP_PREFERENCES_PeriodMinute = "PeriodMinute";
    private static final int START_TIME_PICKER = 0;
    private static final int STOP_TIME_PICKER = 1;
    private static final int PERIOD_TIME_PICKER = 2;

    private boolean mIsActive;

    private Switch mActiveSwitch;
    private TextView mStartTextLabel;
    private TextView mStartTextClock;
    private TextView mStopTextLabel;
    private TextView mStopTextClock;
    private TextView mPeriodTextLabel;
    private TextView mPeriodTextClock;

    private int mStartHour;
    private int mStartMinute;
    private int mStopHour;
    private int mStopMinute;
    private int mPeriodHour;
    private int mPeriodMinute;

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

        RestoreFromSettings();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String sTime;
        if (requestCode == START_TIME_PICKER) {
            if (resultCode == RESULT_OK) {
                mStartHour = data.getIntExtra(MainActivity.APP_Hour, 13);
                mStartMinute = data.getIntExtra(MainActivity.APP_Minute, 0);
                sTime = mStartHour + ":" + mStartMinute;
                mStartTextClock.setText(sTime);
            }
        }
        else
        {
            if (requestCode == STOP_TIME_PICKER) {
                if (resultCode == RESULT_OK) {
                    mStopHour = data.getIntExtra(MainActivity.APP_Hour, 23);
                    mStopMinute = data.getIntExtra(MainActivity.APP_Minute, 0);
                    sTime = mStopHour + ":" + mStopMinute;
                    mStopTextClock.setText(sTime);
                }
            }
            else {
                if (requestCode == PERIOD_TIME_PICKER) {
                    if (resultCode == RESULT_OK) {
                        mPeriodHour = data.getIntExtra(MainActivity.APP_Hour, 1);
                        mPeriodMinute = data.getIntExtra(MainActivity.APP_Minute, 0);
                        sTime = mPeriodHour + ":" + mPeriodMinute;
                        mPeriodTextClock.setText(sTime);
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StoreToSettings();
    }

    @Override
    protected void onPause() {
        super.onPause();
        StoreToSettings();
    }

    public void onActiveChanged(View view) {
        mIsActive = mActiveSwitch.isChecked();
        SetEnabled();
    }

    public void onStartClick(View view) {
        Intent intent = new Intent(MainActivity.this, TimePickerActivity.class);
        intent.putExtra(MainActivity.APP_Hour, mStartHour);
        intent.putExtra(MainActivity.APP_Minute, mStartMinute);
        startActivityForResult(intent, START_TIME_PICKER);
    }
    public void onStopClick(View view) {
        Intent intent = new Intent(MainActivity.this, TimePickerActivity.class);
        intent.putExtra(MainActivity.APP_Hour, mStopHour);
        intent.putExtra(MainActivity.APP_Minute, mStopMinute);
        startActivityForResult(intent, STOP_TIME_PICKER);
    }

    public void onPeriodClicke(View view) {
        Intent intent = new Intent(MainActivity.this, TimePickerActivity.class);
        intent.putExtra(MainActivity.APP_Hour, mPeriodHour);
        intent.putExtra(MainActivity.APP_Minute, mPeriodMinute);
        startActivityForResult(intent, PERIOD_TIME_PICKER);
    }

    protected void StoreToSettings()
    {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean(APP_PREFERENCES_IsActive, mIsActive);
        editor.putInt(APP_PREFERENCES_StartHour, mStartHour);
        editor.putInt(APP_PREFERENCES_StartMinute, mStartMinute);
        editor.putInt(APP_PREFERENCES_StopHour, mStopHour);
        editor.putInt(APP_PREFERENCES_StopMinute, mStopMinute);
        editor.putInt(APP_PREFERENCES_PeriodHour, mPeriodHour);
        editor.putInt(APP_PREFERENCES_PeriodMinute, mPeriodMinute);
        editor.apply();
    }

    protected void RestoreFromSettings()
    {
        if (mSettings.contains(APP_PREFERENCES_IsActive)) {
            // Получаем число из настроек
            mIsActive = mSettings.getBoolean(APP_PREFERENCES_IsActive, true);

            mStartHour = mSettings.getInt(APP_PREFERENCES_StartHour, 13);
            mStartMinute = mSettings.getInt(APP_PREFERENCES_StartMinute, 0);
            mStopHour = mSettings.getInt(APP_PREFERENCES_StopHour, 23);
            mStopMinute = mSettings.getInt(APP_PREFERENCES_StopMinute, 0);
            mPeriodHour = mSettings.getInt(APP_PREFERENCES_PeriodHour, 1);
            mPeriodMinute = mSettings.getInt(APP_PREFERENCES_PeriodMinute, 0);
            // Выводим на экран данные из настроек
            mActiveSwitch.setChecked(mIsActive);
            mStartTextClock.setText(mStartHour + ":" + mStartMinute);
            mStopTextClock.setText(mStopHour + ":" + mStopMinute);
            mPeriodTextClock.setText(mPeriodHour + ":" + mPeriodMinute);
            SetEnabled();
        }
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
