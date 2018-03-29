package com.kotdroid.cpconsumerdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

/**
 * Created by user12 on 29/3/18.
 */

public class RemoteBoundService extends Service {

    private static final int MIN = 0;
    private static final int MAX = 100;
    public static final int GET_COUNT = 0;
    private int randomNumber;

    private boolean shouldGenerateRandomNumber;


    @Nullable @Override public IBinder onBind(Intent intent) {
        return randomNumerMessenger.getBinder();
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


    public void stopRandomNumberGenerator() {
        shouldGenerateRandomNumber = false;
    }

    public int getRandomNumber() {
        return randomNumber;
    }

    public class RandomNumberRequestHandler extends Handler {
        @Override public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_COUNT:
                    Message messageSendRandomNumber = Message.obtain(null, GET_COUNT);
                    messageSendRandomNumber.arg1 = getRandomNumber();
                    try {
                        msg.replyTo.send(messageSendRandomNumber);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private Messenger randomNumerMessenger = new Messenger(new RandomNumberRequestHandler());

}

