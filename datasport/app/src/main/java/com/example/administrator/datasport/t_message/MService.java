package com.example.administrator.datasport.t_message;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/3/7.
 */

public class MService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Messenger(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        Messenger client = msg.replyTo;
                        Message replyMsg = Message.obtain(null, 2);
                        Bundle bundle = new Bundle();
                        bundle.putString("reply", msg.getData().getString("msg") + "    服务端已收到信息");
                        replyMsg.setData(bundle);
                        try {
                            client.send(replyMsg);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }).getBinder();
    }
}
