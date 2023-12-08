package com.example.practicealarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 알람 매니저 인스턴스 생성
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // 알람 설정 버튼 클릭 등의 이벤트를 받아서 알람 설정하는 메소드 호출
        setAlarm();
    }

    private void setAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // 아래에서 시간과 분을 설정합니다. 현재는 예시로 10시 0분으로 설정했습니다.
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 01);

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        // 알람 설정
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "알람이 설정되었습니다.", Toast.LENGTH_SHORT).show();
    }

    public static class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 알람이 울릴 때 수행할 동작을 정의합니다.
            Toast.makeText(context, "알람이 울렸습니다!", Toast.LENGTH_SHORT).show();
            // 여기에 원하는 동작을 추가할 수 있습니다.
        }
    }
}