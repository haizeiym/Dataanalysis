package com.example.administrator.datasport;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.datasport.db.SpotDao;

public class UpdataDataUI extends Activity {
    private EditText heighest, lowest, operationCount, dataRemarks;
    private Button upDown, updata, rightWrong, addRemarks;
    private String ud = "", rw = "";
    private SpotDao dao;
    private SpotModel spotModel;
    // 是否能修改数据
    private boolean isChangeData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_data);
        initView();
        initData();
    }

    private void initView() {
        heighest = (EditText) findViewById(R.id.heighest);
        lowest = (EditText) findViewById(R.id.lowest);
        operationCount = (EditText) findViewById(R.id.operation);
        dataRemarks = (EditText) findViewById(R.id.data_remarks);
        upDown = (Button) findViewById(R.id.up_down);
        updata = (Button) findViewById(R.id.updata);
        rightWrong = (Button) findViewById(R.id.right_wrong);
        addRemarks = (Button) findViewById(R.id.add_remarks);
        dao = new SpotDao(this);
        Bundle bundle = getIntent().getExtras();
        spotModel = (SpotModel) bundle.get("data");
    }

    private void initData() {
        heighest.setText(spotModel.highest);
        lowest.setText(spotModel.lowest);
        operationCount.setText(spotModel.operationCount);
        rw = null == spotModel.rightWrong ? "对" : spotModel.rightWrong;
        rightWrong.setText(rw);
        rightWrong.setTextColor(setwColor(rw));
        ud = null == spotModel.yourself ? "涨" : spotModel.yourself;
        upDown.setText(ud);
        upDown.setTextColor(setColor(ud));
        isChangeData = dao.isTodayData(
                CommintUtils.StringData(CommintUtils.YM), spotModel.data);
        dataRemarks.setText(null == spotModel.remarks ? "" : spotModel.remarks);
        upDown.setOnClickListener(click);
        updata.setOnClickListener(click);
        rightWrong.setOnClickListener(click);
        addRemarks.setOnClickListener(click);
    }

    private OnClickListener click = new OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.right_wrong:
                    CommintUtils.commonClickAlerDialog(UpdataDataUI.this, "之前对错",
                            dao.rw, new ShowDialogClickI() {
                                @Override
                                public void clickChoose(int position) {
                                    int color = Color.RED;
                                    switch (position) {
                                        case 0:
                                            rw = dao.rw[0];
                                            break;
                                        case 1:
                                            rw = dao.rw[1];
                                            color = Color.GREEN;
                                            break;
                                    }
                                    rightWrong.setTextColor(color);
                                    rightWrong.setText(rw);
                                }
                            });
                    break;
                case R.id.up_down:
                    CommintUtils.commonClickAlerDialog(UpdataDataUI.this, "自猜涨跌",
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
                                    upDown.setTextColor(color);
                                    upDown.setText(ud);
                                }
                            });
                    break;
                case R.id.updata:
                    if (!isChangeData) {
                        showToast("只能修改当天数据");
                        break;
                    }
                    String hp = heighest.getText().toString();
                    String lp = lowest.getText().toString();
                    String oc = operationCount.getText().toString();
                    if (!hp.isEmpty() && !lp.isEmpty() && !ud.isEmpty()) {
                        String[] newData = {oc.isEmpty() ? "1" : oc, hp, lp, rw,
                                ud, spotModel.data};
                        dao.updataData(newData);
                        showToast("修改成功");
                        setResult(1);
                        finish();
                    } else {
                        showToast("修改信息不完整");
                    }
                    break;
                case R.id.add_remarks:
                    String content = dataRemarks.getText().toString();
                    if (!content.isEmpty()) {
                        dao.addReamrk(spotModel.data, content);
                        showToast("添加备注成功");
                        setResult(1);
                    } else {
                        showToast("备注未填写");
                    }
                    break;
            }
        }
    };

    private int setColor(String upDown) {
        if (null == upDown || upDown.equals("涨")) {
            return Color.RED;
        } else {
            return Color.GREEN;
        }
    }

    private int setwColor(String rw) {
        if (null == rw || rw.equals("对")) {
            return Color.RED;
        } else {
            return Color.GREEN;
        }
    }

    private void showToast(String text) {
        Toast.makeText(UpdataDataUI.this, text, Toast.LENGTH_SHORT).show();
    }
}
