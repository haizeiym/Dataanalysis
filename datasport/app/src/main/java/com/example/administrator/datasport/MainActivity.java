package com.example.administrator.datasport;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.datasport.db.SpotDao;

public class MainActivity extends AppCompatActivity {
    private TextView data;
    private Button save, spot_data, up_down;
    private EditText buy_point, yesterday_data, today_data, operation_count;
    private SpotDao dao;
    private String ud = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        data = (TextView) findViewById(R.id.data);
        save = (Button) findViewById(R.id.save);
        spot_data = (Button) findViewById(R.id.spot_data);
        up_down = (Button) findViewById(R.id.up_down);
        buy_point = (EditText) findViewById(R.id.buy_point);
        yesterday_data = (EditText) findViewById(R.id.yesterday_data);
        today_data = (EditText) findViewById(R.id.today_data);
        operation_count = (EditText) findViewById(R.id.operation_count);
        dao = new SpotDao(this);
    }

    private void initData() {
        String[] zk = dao.getZK(CommintUtils.StringData(CommintUtils.YM));
        if (null != zk) {
            yesterday_data.setText(zk[0]);
            today_data.setText(zk[1]);
        }
        up_down.setOnClickListener(click);
        save.setOnClickListener(click);
        spot_data.setOnClickListener(click);
        data.setText(CommintUtils.StringData(CommintUtils.YMH));
    }

    private View.OnClickListener click = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
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
                                    up_down.setTextColor(color);
                                    up_down.setText(ud);
                                }
                            });

                    break;
                case R.id.save:
                    String buyPoint = buy_point.getText().toString();
                    String yesterdayData = yesterday_data.getText().toString();
                    String todayData = today_data.getText().toString();
                    String operationCount = operation_count.getText().toString();
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
                        buy_point.setText("");
                        operation_count.setText("");
                        showToast("添加成功");
                    } else {
                        showToast("信息不完整");
                    }
                    break;
                case R.id.spot_data:
                    startActivity(new Intent(MainActivity.this, ShowDataUI.class));
                    break;
            }
        }
    };

    private void showToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
