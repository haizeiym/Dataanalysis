package com.example.administrator.datasport.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.datasport.SpotModel;

import java.util.ArrayList;
import java.util.List;


public class SpotDao {
	private SpotDB spotDB;
	/** 查询 */
	public final int ALL_DATA = 0;
	public final String[] ups = { "涨", "跌" };
	public final String[] rw = { "对", "错" };

	public SpotDao(Context context) {
		spotDB = new SpotDB(context);
	}

	// 增加
	public void addData(SpotModel spotModel) {
		SQLiteDatabase sd = spotDB.getWritableDatabase();
		sd.execSQL("insert into " + spotDB.TABLE_NAME + "(" + spotDB.DATA + ","
						+ spotDB.YESTERDAY_DATA + "," + spotDB.TODAY_DATA + ","
						+ spotDB.BUY_POINT + "," + spotDB.HIGHEST + "," + spotDB.LOWEST
						+ "," + spotDB.UP_DOWN + "," + spotDB.OPERATION_COUNT + ","
						+ spotDB.RIGHT_WRONG + "," + spotDB.YOURSELF_ + ","
						+ spotDB.YEAR_MONTHER + ")" + "values(?,?,?,?,?,?,?,?,?,?,?)",
				new String[] { spotModel.data, spotModel.yesterdayData,
						spotModel.todayData, spotModel.buyPoint,
						spotModel.highest, spotModel.lowest,
						spotModel.upOrDown, spotModel.operationCount,
						spotModel.rightWrong, spotModel.yourself,
						spotModel.yearMonth });
		sd.close();
	}

	// 删除数据
	public void deleData(String timeId) {
		SQLiteDatabase sd = spotDB.getWritableDatabase();
		sd.execSQL("delete from " + spotDB.TABLE_NAME + " where " + spotDB.DATA
				+ "=?", new String[] { timeId });
		sd.close();
	}

	// 修改
	public void updataData(String[] newData) {
		SQLiteDatabase sd = spotDB.getWritableDatabase();
		// 修改操作次数，最高点，最低点，自猜
		sd.execSQL("update " + spotDB.TABLE_NAME + " set "
						+ spotDB.OPERATION_COUNT + "=?," + spotDB.HIGHEST + "=?,"
						+ spotDB.LOWEST + "=?," + spotDB.RIGHT_WRONG + "=?,"
						+ spotDB.YOURSELF_ + "=?" + " where " + spotDB.DATA + "=?",
				newData);
		sd.close();
	}

	// 根据ID添加备注
	public void addReamrk(String timeId, String remarks) {
		SQLiteDatabase sd = spotDB.getWritableDatabase();
		sd.execSQL("update " + spotDB.TABLE_NAME + " set "
						+ spotDB.DATA_REMARKS + "=?" + " where " + spotDB.DATA + "=?",
				new String[] { remarks, timeId });
		sd.close();
	}

	// 存储的数据是否是今天的,不是不能修改
	public boolean isTodayData(String todayTime, String timeId) {
		SQLiteDatabase sd = null;
		Cursor cs = null;
		String csData = "";
		try {
			sd = spotDB.getReadableDatabase();
			cs = sd.rawQuery("select * from " + spotDB.TABLE_NAME + " where "
					+ spotDB.DATA + "=?", new String[] { timeId });
			if (cs != null) {
				if (cs.moveToFirst()) {
					csData = getString(cs, spotDB.YEAR_MONTHER);
				}
			}
		} finally {
			if (sd != null) {
				sd.close();
			}
			if (cs != null) {
				cs.close();
			}
		}

		return null == csData ? false : todayTime.equals(csData);
	}

	// 根据年月获取昨收，开盘
	public String[] getZK(String ym) {
		String z = "", k = "";
		SQLiteDatabase sd = null;
		Cursor cs = null;
		try {
			sd = spotDB.getReadableDatabase();
			cs = sd.rawQuery("select * from " + spotDB.TABLE_NAME + " where "
					+ spotDB.YEAR_MONTHER + "=?", new String[] { ym });
			if (cs != null) {
				if (cs.moveToLast()) {
					z = getString(cs, spotDB.YESTERDAY_DATA);
					k = getString(cs, spotDB.TODAY_DATA);
				}
			}
		} finally {
			if (sd != null) {
				sd.close();
			}
			if (cs != null) {
				cs.close();
			}
		}
		return z.isEmpty() || k.isEmpty() ? null : new String[] { z, k };
	}

	// 获取数据
	public List<SpotModel> getData(int type) {
		List<SpotModel> list = new ArrayList<SpotModel>();
		SQLiteDatabase sd = null;
		Cursor cs = null;
		try {
			sd = spotDB.getReadableDatabase();
			switch (type) {
				case ALL_DATA:
					cs = queryAll(sd);
					break;
			}
			if (cs != null) {
				while (cs.moveToNext()) {
					list.add(setData(cs));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sd != null) {
				sd.close();
			}
			if (cs != null) {
				cs.close();
			}
		}
		return list;
	}

	// 查询所有
	private Cursor queryAll(SQLiteDatabase sd) {
		return sd.query(spotDB.TABLE_NAME, null, null, null, null, null,
				"_id desc");
	}

	private SpotModel setData(Cursor cs) {
		SpotModel spotModel = new SpotModel();
		spotModel.data = getString(cs, spotDB.DATA);
		spotModel.yesterdayData = getString(cs, spotDB.YESTERDAY_DATA);
		spotModel.todayData = getString(cs, spotDB.TODAY_DATA);
		spotModel.buyPoint = getString(cs, spotDB.BUY_POINT);
		spotModel.highest = getString(cs, spotDB.HIGHEST);
		spotModel.lowest = getString(cs, spotDB.LOWEST);
		spotModel.upOrDown = getString(cs, spotDB.UP_DOWN);
		spotModel.operationCount = getString(cs, spotDB.OPERATION_COUNT);
		spotModel.rightWrong = getString(cs, spotDB.RIGHT_WRONG);
		spotModel.yourself = getString(cs, spotDB.YOURSELF_);
		spotModel.remarks = getString(cs, spotDB.DATA_REMARKS);
		return spotModel;
	}

	private String getString(Cursor cs, String key) {
		return cs.getString(cs.getColumnIndex(key));
	}

}
