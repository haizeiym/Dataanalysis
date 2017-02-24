package com.example.administrator.datasport;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.datasport.databinding.RxGetDataBinding;

/**
 * Created by Administrator on 2017/2/22.
 */

public class RXGetDataUI extends Activity {
    private RxGetDataBinding binding;
    private PicModel picModel;

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
        picModel.setPicName("点击测试");
        //25879079
        binding.setPicModel(picModel);
        binding.setJustClick(new Click());
    }

    public class Click {
        public void click(View view) {
            picModel.setPicName("点击事件测试");
        }
    }


}
