package com.ljc.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.Map;

public class BroadCastManager {
    private static final String TAG = "BroadCastManager";
    private WeakReference<Map<String, BroadcastReceiver>> receivers;

    private BroadCastManager() {
        receivers = new WeakReference<>(receivers.get());
    }

    private static class BroadCastSingle {
        private static BroadCastManager INSTANCE = new BroadCastManager();
    }

    public static BroadCastManager getInstance() {
        return BroadCastSingle.INSTANCE;
    }

    /**
     * 注册广播
     *
     * @param context                 上下文
     * @param broadCastReceiverAction 广播Action字符串
     * @param receiver                广播接收器
     */
    public void registerBroadCastReceiver(Context context, String broadCastReceiverAction, BroadcastReceiver receiver) {
        if (TextUtils.isEmpty(broadCastReceiverAction) || receiver == null) {
            Log.e(TAG, "需要注册的广播Action或receiver未设置");
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(broadCastReceiverAction);
        registerBroadCastReceiver(context, broadCastReceiverAction, intentFilter, receiver);
    }

    public void registerBroadCastReceiver(Context context, String broadCastReceiverAction, IntentFilter intentFilter, BroadcastReceiver receiver) {
        if (TextUtils.isEmpty(broadCastReceiverAction) || receiver == null) {
            Log.e(TAG, "需要注册的广播Action或receiver未设置");
            return;
        }
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, intentFilter);
        assert receivers != null && receivers.get() != null;
        receivers.get().put(broadCastReceiverAction, receiver);
        Log.d(TAG, "注册广播:" + broadCastReceiverAction);
    }

    /**
     * 反注册广播
     *
     * @param context                 上下文
     * @param broadCastReceiverAction 广播Action字符串
     */
    public void unRegisterBroadCastReceiver(Context context, String broadCastReceiverAction) {
        if (TextUtils.isEmpty(broadCastReceiverAction)) {
            Log.e(TAG, "需要反注册的广播Action为空");
            return;
        }
        assert receivers != null && receivers.get() != null;
        BroadcastReceiver broadcastReceiver = receivers.get().get(broadCastReceiverAction);
        if (broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver);
            receivers.get().remove(broadCastReceiverAction);
            Log.d(TAG, "反注册广播:" + broadCastReceiverAction);
        }
    }

    /**
     * 发送广播
     *
     * @param context                 上下文
     * @param broadCastReceiverAction 广播Action字符串
     */
    public void sendBroadCast(Context context, String broadCastReceiverAction) {
        sendBroadCast(context, broadCastReceiverAction, null);
    }

    /**
     * 发送广播
     *
     * @param context                 上下文
     * @param broadCastReceiverAction 广播Action字符串
     * @param datas                   传递参数map
     */
    public void sendBroadCast(Context context, String broadCastReceiverAction, Map<String, Object> datas) {
        if (TextUtils.isEmpty(broadCastReceiverAction)) {//没有广播action
            Log.e(TAG, "想要发送的广播Action为空");
            return;
        }
        Intent intent = new Intent(broadCastReceiverAction);
        if (null != datas) {
            putExtra(intent, datas);
        }
        send(context, intent);
    }

    private void send(Context context, Intent intent) {
        Log.d(TAG, "发送广播:" + intent.getAction());
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    /**
     * Intent传参-加入参数
     *
     * @param intent ""
     * @param datas  参数map
     */
    private void putExtra(Intent intent, Map<String, Object> datas) {
        if (datas != null && !datas.isEmpty()) {
            for (Map.Entry<String, Object> entry : datas.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (TextUtils.isEmpty(key) || value == null) {
                    continue;
                }
                checkValueType(intent, key, value);
            }
        }
    }

    /**
     * Intent传参-检查参数类型
     *
     * @param intent ""
     * @param key    参数key
     * @param value  参数值
     */
    private void checkValueType(Intent intent, String key, Object value) {
        if (value instanceof Byte) {
            Log.d(TAG, "Byte");
            intent.putExtra(key, (Byte) value);
        } else if (value instanceof Byte[]) {
            Log.d(TAG, "Byte[]");
            intent.putExtra(key, (Byte[]) value);
        } else if (value instanceof String) {
            Log.d(TAG, "String");
            intent.putExtra(key, (String) value);
        } else if (value instanceof String[]) {
            Log.d(TAG, "String[]");
            intent.putExtra(key, (String[]) value);
        } else if (value instanceof Character) {
            Log.d(TAG, "Character");
            intent.putExtra(key, (Character) value);
        } else if (value instanceof Character[]) {
            Log.d(TAG, "Character[]");
            intent.putExtra(key, (Character[]) value);
        } else if (value instanceof CharSequence) {
            Log.d(TAG, "CharSequence");
            intent.putExtra(key, (CharSequence) value);
        } else if (value instanceof CharSequence[]) {
            Log.d(TAG, "CharSequence[]");
            intent.putExtra(key, (CharSequence[]) value);
        } else if (value instanceof Short) {
            Log.d(TAG, "Short");
            intent.putExtra(key, (Short) value);
        } else if (value instanceof Short[]) {
            Log.d(TAG, "Short[]");
            intent.putExtra(key, (Short[]) value);
        } else if (value instanceof Boolean) {
            Log.d(TAG, "Boolean");
            intent.putExtra(key, (Boolean) value);
        } else if (value instanceof Boolean[]) {
            Log.d(TAG, "Boolean[]");
            intent.putExtra(key, (Boolean[]) value);
        } else if (value instanceof Integer) {
            Log.d(TAG, "Integer");
            intent.putExtra(key, (Integer) value);
        } else if (value instanceof Integer[]) {
            Log.d(TAG, "Integer[]");
            intent.putExtra(key, (Integer[]) value);
        } else if (value instanceof Long) {
            Log.d(TAG, "Long");
            intent.putExtra(key, (Long) value);
        } else if (value instanceof Long[]) {
            Log.d(TAG, "Long[]");
            intent.putExtra(key, (Long[]) value);
        } else if (value instanceof Float) {
            Log.d(TAG, "Float");
            intent.putExtra(key, (Float) value);
        } else if (value instanceof Float[]) {
            Log.d(TAG, "Float[]");
            intent.putExtra(key, (Float[]) value);
        } else if (value instanceof Double) {
            Log.d(TAG, "Double");
            intent.putExtra(key, (Double) value);
        } else if (value instanceof Double[]) {
            Log.d(TAG, "Double[]");
            intent.putExtra(key, (Double[]) value);
        } else if (value instanceof Bundle) {
            Log.d(TAG, "Bundle");
            intent.putExtra(key, (Bundle) value);
        } else if (value instanceof Bundle[]) {
            Log.d(TAG, "Bundle[]");
            intent.putExtra(key, (Bundle[]) value);
        } else if (value instanceof Parcelable) {//同作用：Parcelable.class.isAssignableFrom(value.getClass())
            Log.d(TAG, "Parcelable");
            intent.putExtra(key, (Parcelable) value);
        } else if (value instanceof Parcelable[]) {
            Log.d(TAG, "Parcelable[]");
            intent.putExtra(key, (Parcelable[]) value);
        } else if (value instanceof Serializable) {//同作用：Serializable.class.isAssignableFrom(value.getClass())
            Log.d(TAG, "Serializable");
            intent.putExtra(key, (Serializable) value);
        } else {
            throw (new RuntimeException("不支持的Intent传递类型," + value.getClass().getSimpleName() + "可能为未实现Parcelable或Serializable接口的类"));
        }
        Log.d(TAG, "key=" + key + ",value=" + value);
    }
}
