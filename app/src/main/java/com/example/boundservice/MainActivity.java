package com.example.boundservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BoundedService mBoundService;
    Boolean mServiceBound = false;
    Button startService;
    TextView serviceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService = (Button) findViewById(R.id.start);
        serviceText = (TextView) findViewById(R.id.serviceText);

        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomNuber = mBoundService.genRandomNumber();
                serviceText.setText(String.valueOf(randomNuber));
            }
        });

    }
    @Override
    protected void onStart() {
        Intent intent = new Intent(this,BoundedService.class);
        bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
        super.onStart();
    }
    @Override
    protected void onStop() {
        unbindService(mServiceConnection);
        stopService(new Intent(this,BoundedService.class));
        super.onStop();
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            mServiceBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(getApplicationContext(),"Service connected",Toast.LENGTH_SHORT).show();
            BoundedService.LocalBoundedService myBinder = (BoundedService.LocalBoundedService) service;
            mBoundService = myBinder.getService();
            mServiceBound = true;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(),"Service Disconnected",Toast.LENGTH_SHORT).show();
    }

    public void getMessage(View view) {

    }
}
