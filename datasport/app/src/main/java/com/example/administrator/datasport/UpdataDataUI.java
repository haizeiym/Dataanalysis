package com.example.administrator.datasport;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.datasport.databinding.UpdateDataBinding;
import com.example.administrator.datasport.db.SpotDao;

import static com.example.administrator.datasport.R.id.updata;

public class UpdataDataUI extends Activity {
    private String ud = "", rw = "";
    private SpotDao dao;
    private SpotModel spotModel;
    // 是否能修改数据
    private boolean isChangeData = false;
    private UpdateDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.update_data);
        dao = new SpotDao(this);
        Bundle bundle = getIntent().getExtras();
        spotModel = (SpotModel) bundle.get("data");
        binding.setClick(new Click());
    }

    private void initData() {
        binding.heighest.setText(spotModel.highest);
        binding.lowest.setText(spotModel.lowest);
        binding.operation.setText(spotModel.operationCount);
        rw = null == spotModel.rightWrong ? "对" : spotModel.rightWrong;
        binding.rightWrong.setText(rw);
        binding.rightWrong.setTextColor(setwColor(rw));
        ud = null == spotModel.yourself ? "涨" : spotModel.yourself;
        binding.upDown.setText(ud);
        binding.upDown.setTextColor(setColor(ud));
        isChangeData = dao.isTodayData(CommintUtils.StringData(CommintUtils.YM), spotModel.data);
        binding.dataRemarks.setText(null == spotModel.remarks ? "" : spotModel.remarks);
    }

    public class Click {
        public void click(View view) {
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
                                    binding.rightWrong.setTextColor(color);
                                    binding.rightWrong.setText(rw);
                                }
                            });
                    break;
                case R.id.up_down:
                    //USER@MACHINE:~$ sudo apt-get install flex bison gperf libsdl-dev libesd0-dev libwxgtk2.6-dev build-essential zip curl valgrind
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
                                    binding.upDown.setTextColor(color);
                                    binding.upDown.setText(ud);
                                }
                            });
                    break;
                case updata:
                    if (!isChangeData) {
                        showToast("只能修改当天数据");
                        break;
                    }
                    String hp = binding.heighest.getText().toString();
                    String lp = binding.lowest.getText().toString();
                    String oc = binding.operation.getText().toString();
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
                    String content = binding.dataRemarks.getText().toString();
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
    }

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
