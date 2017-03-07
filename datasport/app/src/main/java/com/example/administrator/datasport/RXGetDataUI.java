package com.example.administrator.datasport;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;

import com.example.administrator.datasport.databinding.RxGetDataBinding;
import com.example.administrator.datasport.t_message.MService;

/**
 * Created by Administrator on 2017/2/22.
 */

public class RXGetDataUI extends Activity {
    private RxGetDataBinding binding;
    private PicModel picModel;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Messenger ms = new Messenger(service);
            Message msg = Message.obtain(null, 1);
            Bundle data = new Bundle();
            data.putString("msg", "这是个测试");
            msg.setData(data);
            msg.replyTo = new Messenger(new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 2:
                            picModel.setPicName(msg.getData().getString("reply"));
                            break;
                    }
                }
            });
            try {
                ms.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.rx_get_data);
        picModel = new PicModel();
    }

    private void initData() {
        picModel.setPicName("服务进程测试");
        binding.setPicModel(picModel);
        binding.setJustClick(new Click());
    }

    public class Click {
        public void click(View view) {
            bindService(new Intent(RXGetDataUI.this, MService.class), serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
