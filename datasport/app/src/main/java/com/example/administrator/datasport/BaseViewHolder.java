package com.example.administrator.datasport;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class BaseViewHolder extends RecyclerView.ViewHolder {
	TextView dataTime, yesterdayData, todayData, buyPoint, operationCount,
			upDown;

	public BaseViewHolder(View contentView) {
		super(contentView);
		dataTime = (TextView) contentView.findViewById(R.id.data_time);
		yesterdayData = (TextView) contentView
				.findViewById(R.id.yesterday_data);
		todayData = (TextView) contentView.findViewById(R.id.today_data);
		buyPoint = (TextView) contentView.findViewById(R.id.bug_point);
		operationCount = (TextView) contentView
				.findViewById(R.id.operation_count);
		upDown = (TextView) contentView.findViewById(R.id.up_down);
	}

}
