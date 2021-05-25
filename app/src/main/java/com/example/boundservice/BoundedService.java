package com.example.boundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Random;

public class BoundedService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private IBinder mBinder =  new LocalBoundedService();
    Random randomNumber = new Random();

    @Nullable
    @Override
    public boolean onUnbind(Intent intent) {

        return true;
    }
    public int genRandomNumber()
    {
        return randomNumber.nextInt(100);
    }

    public class LocalBoundedService extends Binder {
        BoundedService getService() {
            return BoundedService.this;
        }
    }
}
