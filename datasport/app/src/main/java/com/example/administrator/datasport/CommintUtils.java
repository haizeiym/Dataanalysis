package com.example.administrator.datasport;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

class CommintUtils {
    // 年月日时分秒
    static final int YMH = 0;
    // 年月日
    static final int YM = 1;

    /**
     * 公用alertDialog
     */
    static void commonAlertDialog(Context context, String title,
                                  String msg, String sureText, String cancleText,
                                  final AlertDialogI alertDialogI) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(sureText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                alertDialogI.sure(dialog, which);
                            }
                        }).setNegativeButton(cancleText, null).show();
    }

    /**
     * 公用dialog点击
     */
    static void commonClickAlerDialog(Context context, String titleName,
                                      String[] itmes, final ShowDialogClickI showClick) {
        Dialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(titleName)
                .setItems(itmes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        showClick.clickChoose(position);
                    }
                }).setIcon(R.mipmap.ic_launcher).create();
        alertDialog.show();
    }

    // 获取时间
    static String StringData(int type) {
        String timeData = "";
        String mYear;
        String mMonth;
        String mDay;
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        switch (type) {
            case YMH:
                String mHour,
                        mMin,
                        mSec;
                int mWay;
                mHour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
                mMin = String.valueOf(c.get(Calendar.MINUTE));
                mSec = String.valueOf(c.get(Calendar.SECOND));
                mWay = c.get(Calendar.DAY_OF_WEEK);
                String xq = "";
                switch (mWay) {
                    case 1:
                        xq = "天";
                        break;
                    case 2:
                        xq = "一";
                        break;
                    case 3:
                        xq = "二";
                        break;
                    case 4:
                        xq = "三";
                        break;
                    case 5:
                        xq = "四";
                        break;
                    case 6:
                        xq = "五";
                        break;
                    case 7:
                        xq = "六";
                        break;
                }
                timeData = mYear + "年" + mMonth + "月" + mDay + "日" + mHour + "时"
                        + mMin + "分" + mSec + "秒" + "/星期" + xq;
                break;

            case YM:
                timeData = mYear + "年" + mMonth + "月" + mDay + "日";
                break;
        }

        return timeData;
    }

}
