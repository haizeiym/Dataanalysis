package com.example.administrator.datasport;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

class BaseViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding dataBinding;

    public void setDataBinding(ViewDataBinding dataBinding) {
        this.dataBinding = dataBinding;
    }

    public ViewDataBinding getDataBinding() {
        return dataBinding;
    }

    BaseViewHolder(View contentView) {
        super(contentView);
    }

}
