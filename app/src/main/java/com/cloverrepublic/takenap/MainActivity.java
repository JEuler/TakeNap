package com.cloverrepublic.takenap;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by itere on 19.04.2016.
 */
public class MainActivity extends AppCompatActivity {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("alarm", true);
        alarmIntent = PendingIntent.getActivity(this, 0, intent, 0);
        (findViewById(R.id.stop_timer)).setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getBooleanExtra("alarm", false)) {
            Toast.makeText(MainActivity.this, "FUCK YOU", Toast.LENGTH_SHORT).show();
            (findViewById(R.id.stop_timer)).setVisibility(View.VISIBLE);
            mediaPlayer = MediaPlayer.create(this, R.raw.alarmclock);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();

        }
    }

    public void test(View v) {
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        1 * 10 * 1000, alarmIntent);
        onBackPressed();
    }


    public void on15MinClick(View view) {
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        15 * 60 * 1000, alarmIntent);
    }

    public void on30MinClick(View view) {
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        30 * 60 * 1000, alarmIntent);
    }

    public void on45MinClick(View view) {
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        45 * 60 * 1000, alarmIntent);
    }

    public void on60MinClick(View view) {
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        60 * 60 * 1000, alarmIntent);
    }

    public void onCustomClick(View view) {

    }

    public void onRateClick(View view) {

    }

    public void onStopClick(View view) {
        mediaPlayer.stop();
        view.setVisibility(View.GONE);
    }
}
