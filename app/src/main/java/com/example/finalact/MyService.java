package com.example.finalact;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MyService extends Service {
    private String stuname;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefs", WelcomeActivity.MODE_PRIVATE);
        this.stuname = sharedPreferences.getString("stuname", null);

        WelcomeActivity ins = new WelcomeActivity();
        ins.deleteData(this.stuname);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        this.stopSelf();
    }
}
