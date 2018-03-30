package com.kotdroid.cpconsumerdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

/**
 * Created by user12 on 29/3/18.
 */

public class LocalBindingService extends Service {

    private static final int MIN = 0;
    private static final int MAX = 100;
    private int randomNumber;

    private boolean shouldGenerateRandomNumber;

    private IBinder serviceBinder = new MyServiceBinder();

    @Nullable @Override public IBinder onBind(Intent intent) {
        return serviceBinder;
    }

    @Override public void onCreate() {
        super.onCreate();
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        shouldGenerateRandomNumber = true;
        new Thread(new Runnable() {
            @Override public void run() {
                startRandomNumberGenerator();
            }
        }).start();
        return START_STICKY;
    }

    @Override public void onDestroy() {
        super.onDestroy();
        stopRandomNumberGenerator();
        Log.d("ServiceDemo", "onDestroy()");
    }

    public void startRandomNumberGenerator() {
        while (shouldGenerateRandomNumber) {
            try {
                Thread.sleep(1000);
                randomNumber = new Random().nextInt(MAX) + MIN;
                Log.d("ServiceDemo", "The generated random number is :" + randomNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

     class MyServiceBinder extends Binder {

         LocalBindingService getService() {
            return LocalBindingService.this;
        }
    }

    public void stopRandomNumberGenerator() {
        shouldGenerateRandomNumber = false;
    }

    public int getRandomNumber() {
        return randomNumber;
    }
}

