package com.example.administrator.datasport;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

public class RecAdapter extends RecyclerView.Adapter<BaseViewHolder> {
	private Context context;
	private List<SpotModel> spotDataList;
	private OnItemListener onItemClickListener;
	private LayoutInflater inflater;
	public interface OnItemListener {
		void onItemClick(int position);

		void onLongItemClick(int position);
	}

	public void setListener(OnItemListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public RecAdapter(Context context, List<SpotModel> spotDataList) {
		this.context = context;
		this.spotDataList = spotDataList;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getItemCount() {
		return spotDataList.size();
	}

	@Override
	public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new BaseViewHolder(inflater.inflate(R.layout.show_data_item,parent,false));
	}

	@Override
	public void onBindViewHolder(BaseViewHolder holder, final int position) {
		SpotModel spotModel = spotDataList.get(position);
		holder.dataTime.setText(spotModel.data);
		holder.yesterdayData.setText("昨收：" + spotModel.yesterdayData);
		holder.todayData.setText("今开：" + spotModel.todayData);
		holder.buyPoint.setText("购买点位：" + spotModel.buyPoint);
		holder.operationCount.setText("操作次数：" + spotModel.operationCount);
		holder.upDown.setText(spotModel.upOrDown);
		holder.upDown.setTextColor(setColor(spotModel.upOrDown));
		holder.itemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (onItemClickListener != null) {
					onItemClickListener.onItemClick(position);
				}
			}
		});
		holder.itemView.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View view) {
				if (onItemClickListener != null) {
					onItemClickListener.onLongItemClick(position);
				}
				return false;
			}
		});
	}

	public void refresh(List<SpotModel> spotDataList) {
		this.spotDataList = spotDataList;
		notifyDataSetChanged();
	}

	private int setColor(String upDown) {
		if (upDown.equals("涨")) {
			return Color.RED;
		} else {
			return Color.GREEN;
		}
	}

}
