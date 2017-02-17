package com.example.administrator.datasport.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SpotDB extends SQLiteOpenHelper {
    final String TABLE_NAME = "spot_data";
    // 今天日期
    final String DATA = "data";
    // 昨收
    final String YESTERDAY_DATA = "yesterday_data";
    // 今开
    final String TODAY_DATA = "today_data";
    // 买的点位
    final String BUY_POINT = "buy_point";
    // 最高点
    final String HIGHEST = "highest";
    // 最低
    final String LOWEST = "lowest";
    // 是涨跌
    final String UP_DOWN = "up_down";
    // 操作次数
    final String OPERATION_COUNT = "operation_count";
    // 是否正确
    final String RIGHT_WRONG = "right_wrong";
    // 自猜
    final String YOURSELF_ = "yourself";
    // 年月
    final String YEAR_MONTHER = "year_monther";
    // 备注
    final String DATA_REMARKS = "data_remarks";

    SpotDB(Context context) {
        super(context, "spot.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TEXT = " TEXT";
        String TEXT_POINT = TEXT + ",";
        String CREATE_TABLE = "create table if not exists ";
        String _ID = "_id INTEGER PRIMARY KEY AUTOINCREMENT,";
        db.execSQL(CREATE_TABLE + TABLE_NAME + "("
                + _ID
                + DATA + TEXT_POINT
                + YESTERDAY_DATA + TEXT_POINT
                + TODAY_DATA + TEXT_POINT
                + HIGHEST + TEXT_POINT
                + LOWEST + TEXT_POINT
                + BUY_POINT + TEXT_POINT
                + OPERATION_COUNT + TEXT_POINT
                + YEAR_MONTHER + TEXT_POINT
                + UP_DOWN + TEXT_POINT
                + YOURSELF_ + TEXT_POINT
                + RIGHT_WRONG + TEXT_POINT
                + DATA_REMARKS + TEXT + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
