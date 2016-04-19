package com.cloverrepublic.takenap;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
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

    void showMessage(final int multiplier) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Take Nap");
        builder.setMessage("Сейчас мы свернем приложение и когда пройдет время сработает звуковой сигнал и вибрация.");

        builder.setPositiveButton("Продолжить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() +
                                multiplier * 60 * 1000, alarmIntent);
                onBackPressed();
            }
        });
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getBooleanExtra("alarm", false)) {
            (findViewById(R.id.min15_button)).setVisibility(View.GONE);
            (findViewById(R.id.min30_button)).setVisibility(View.GONE);
            (findViewById(R.id.min45_button)).setVisibility(View.GONE);
            (findViewById(R.id.min60_button)).setVisibility(View.GONE);
            (findViewById(R.id.minCustom_button)).setVisibility(View.GONE);
            Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            long[] pattern = {0, 300, 100};
            v.vibrate(pattern, 0);
            Toast.makeText(MainActivity.this, "Напоминание сработало!", Toast.LENGTH_SHORT).show();
            (findViewById(R.id.stop_timer)).setVisibility(View.VISIBLE);
            mediaPlayer = MediaPlayer.create(this, R.raw.alarmclock);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();

        } else if (mediaPlayer != null) {
            mediaPlayer.stop();
            Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            v.cancel();
            (findViewById(R.id.min15_button)).setVisibility(View.VISIBLE);
            (findViewById(R.id.min30_button)).setVisibility(View.VISIBLE);
            (findViewById(R.id.min45_button)).setVisibility(View.VISIBLE);
            (findViewById(R.id.min60_button)).setVisibility(View.VISIBLE);
            (findViewById(R.id.minCustom_button)).setVisibility(View.VISIBLE);
        }
    }


    public void on15MinClick(View view) {
        showMessage(15);
    }

    public void on30MinClick(View view) {
        showMessage(30);
    }

    public void on45MinClick(View view) {
        showMessage(45);
    }

    public void on60MinClick(View view) {
        showMessage(60);
    }

    public void onCustomClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Take Nap");

        builder.setMessage("Введите количество минут:");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
        builder.setView(input, 40, 0, 40, 0);

        builder.setPositiveButton("Продолжить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() +
                                (Integer.parseInt(input.getText().toString()) * 60 * 1000), alarmIntent);
                onBackPressed();
            }
        });
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void onRateClick(View view) {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    public void onStopClick(View view) {
        mediaPlayer.stop();
        view.setVisibility(View.GONE);
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        v.cancel();
        (findViewById(R.id.min15_button)).setVisibility(View.VISIBLE);
        (findViewById(R.id.min30_button)).setVisibility(View.VISIBLE);
        (findViewById(R.id.min45_button)).setVisibility(View.VISIBLE);
        (findViewById(R.id.min60_button)).setVisibility(View.VISIBLE);
        (findViewById(R.id.minCustom_button)).setVisibility(View.VISIBLE);
    }

    public void onOtherAppsClick(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/developer?id=Clover+Republic")));
    }
}
