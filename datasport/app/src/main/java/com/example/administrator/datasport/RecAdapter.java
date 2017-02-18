package com.example.administrator.datasport;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

import java.util.List;

class RecAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<SpotModel> spotDataList;
    private OnItemListener onItemClickListener;
    private LayoutInflater inflater;

    interface OnItemListener {
        void onItemClick(int position);

        void onLongItemClick(int position);
    }

    void setListener(OnItemListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    RecAdapter(Context context, List<SpotModel> spotDataList) {
        this.spotDataList = spotDataList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return spotDataList.size();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.show_data_item, parent, false);
        BaseViewHolder viewHolder = new BaseViewHolder(viewDataBinding.getRoot());
        viewHolder.setDataBinding(viewDataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        holder.getDataBinding().setVariable(com.example.administrator.datasport.BR.spotModel, spotDataList.get(position));

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

    void refresh(List<SpotModel> spotDataList) {
        this.spotDataList = spotDataList;
        notifyDataSetChanged();
    }
}
