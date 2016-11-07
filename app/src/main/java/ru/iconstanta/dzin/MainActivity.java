package ru.iconstanta.dzin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.Context;
import android.view.View;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "dzin_settings";
    public static final String APP_PREFERENCES_IsActive = "IsActive";
    public static final String APP_PREFERENCES_Start = "Start";
    public static final String APP_PREFERENCES_Stop = "Stop";
    public static final String APP_PREFERENCES_Period = "Period";

    private boolean mIsActive;

    private Switch mActiveSwitch;
    private TextView mStartTextLabel;
    private TextClock mStartTextClock;
    private TextView mStopTextLabel;
    private TextClock mStopTextClock;
    private TextView mPeriodTextLabel;
    private TextClock mPeriodTextClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        mActiveSwitch = (Switch) findViewById(R.id.ActiveSwitch);
        mStartTextLabel = (TextView) findViewById(R.id.StartTextLabel);
        mStartTextClock = (TextClock) findViewById(R.id.StartTextValue);
        mStopTextLabel = (TextView) findViewById(R.id.StopTextLabel);
        mStopTextClock = (TextClock) findViewById(R.id.StopTextValue);
        mPeriodTextLabel = (TextView) findViewById(R.id.PeriodTextLabel);
        mPeriodTextClock = (TextClock) findViewById(R.id.PeriodTextValue);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSettings.contains(APP_PREFERENCES_IsActive)) {
            // Получаем число из настроек
            mIsActive = mSettings.getBoolean(APP_PREFERENCES_IsActive, true);
            // Выводим на экран данные из настроек
            mActiveSwitch.setChecked(mIsActive);
            SetEnabled();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean(APP_PREFERENCES_IsActive, mIsActive);
        editor.apply();
    }

    public void onActiveChanged(View view) {
        mIsActive = mActiveSwitch.isChecked();
        SetEnabled();
    }

    public void onStartClick(View view) {
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
