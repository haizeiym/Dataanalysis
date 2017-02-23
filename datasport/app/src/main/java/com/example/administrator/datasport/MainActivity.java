package com.example.administrator.datasport;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.datasport.databinding.ActivityMainBinding;
import com.example.administrator.datasport.db.SpotDao;

public class MainActivity extends AppCompatActivity {
    private SpotDao dao;
    private String ud = "";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        dao = new SpotDao(this);
    }

    private void initData() {
        String[] zk = dao.getZK(CommintUtils.StringData(CommintUtils.YM));
        if (null != zk) {
            binding.yesterdayData.setText(zk[0]);
            binding.todayData.setText(zk[1]);
        }
        binding.setData(CommintUtils.StringData(CommintUtils.YMH));
        binding.setClick(new Click());
    }

    public class Click {
        public void click(View view) {
            switch (view.getId()) {
                case R.id.up_down:
                    CommintUtils.commonClickAlerDialog(MainActivity.this, "选择涨跌",
                            dao.ups, new ShowDialogClickI() {
                                @Override
                                public void clickChoose(int position) {
                                    int color = Color.RED;
                                    switch (position) {
                                        case 0:
                                            ud = dao.ups[0];
                                            break;
                                        case 1:
                                            ud = dao.ups[1];
                                            color = Color.GREEN;
                                            break;
                                    }
                                    binding.upDown.setTextColor(color);
                                    binding.upDown.setText(ud);
                                }
                            });

                    break;
                case R.id.save:
                    String buyPoint = binding.buyPoint.getText().toString();
                    String yesterdayData = binding.yesterdayData.getText().toString();
                    String todayData = binding.todayData.getText().toString();
                    String operationCount = binding.operationCount.getText().toString();
                    if (!buyPoint.isEmpty() && !yesterdayData.isEmpty()
                            && !todayData.isEmpty() && !operationCount.isEmpty()
                            && !ud.isEmpty()) {
                        SpotModel spotModel = new SpotModel();
                        spotModel.data = CommintUtils.StringData(CommintUtils.YMH);
                        spotModel.yearMonth = CommintUtils
                                .StringData(CommintUtils.YM);
                        spotModel.buyPoint = buyPoint;
                        spotModel.yesterdayData = yesterdayData;
                        spotModel.todayData = todayData;
                        spotModel.operationCount = operationCount;
                        spotModel.upOrDown = ud;
                        dao.addData(spotModel);
                        binding.buyPoint.setText("");
                        showToast("添加成功");
                    } else {
                        showToast("信息不完整");
                    }
                    break;
                case R.id.spot_data:
                    startActivity(new Intent(MainActivity.this, ShowDataUI.class));
                    break;
                case R.id.pic_show:
                    startActivity(new Intent(MainActivity.this, RXGetDataUI.class));
                    break;
            }
        }
    }

    private void showToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
