package com.example.practicealarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 알람 매니저 생성
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Broadcast Receiver 등록
        registerReceiver(new AlarmReceiver(), new IntentFilter("ALARM_ACTION"));

        // 정시 알람 설정
        setNoonAlarm();
    }

    // 정시 알람 설정
    private void setNoonAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 19); // 정시
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent("ALARM_ACTION");
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        // API Level에 따라 알람 설정 방법이 다르므로 버전에 따라 다른 방법 사용
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        Log.d("Alarm", "Noon alarm set.");
    }

    // Broadcast Receiver 클래스 정의
    public class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 알람이 울릴 때 수행할 작업을 여기에 구현
            Log.d("Alarm", "Alarm! It's noon.");
            // 여기에 원하는 동작을 추가할 수 있습니다.
        }
    }
}