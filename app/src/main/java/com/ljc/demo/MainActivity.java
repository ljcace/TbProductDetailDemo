package com.ljc.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ljc.broadcast.BroadCastManager;

public class MainActivity extends AppCompatActivity {
    public static final String BROADCAST_ACTION_EXP = "com.ljc.exp";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        findViewById(R.id.tv_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProductDetailActivity.class));
            }
        });
        BroadCastManager.getInstance().registerBroadCastReceiver(this, BROADCAST_ACTION_EXP, broadcastReceiver);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("BroadCastManager", "收到广播:" + intent.getAction());
            Bundle bundle = intent.getExtras();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BroadCastManager.getInstance().unRegisterBroadCastReceiver(this, BROADCAST_ACTION_EXP);
    }
}
