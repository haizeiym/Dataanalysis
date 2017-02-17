package com.example.administrator.datasport;

import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.administrator.datasport.db.SpotDao;


public class ShowDataUI extends Activity {
    private SpotDao dao;
    private List<SpotModel> showDataList;
    private RecAdapter adapter;
    private RecyclerView recListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_data);
        initView();
        initData();
    }

    private void initView() {
        recListView = (RecyclerView) findViewById(R.id.show_data);
        dao = new SpotDao(this);
    }

    private void initData() {
        showDataList = dao.getData(dao.ALL_DATA);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                ShowDataUI.this);
        recListView.setLayoutManager(layoutManager);
        adapter = new RecAdapter(ShowDataUI.this, showDataList);
        recListView.setAdapter(adapter);
        recListView.setItemAnimator(new DefaultItemAnimator());
        adapter.setListener(new RecAdapter.OnItemListener() {

            @Override
            public void onLongItemClick(final int position) {
                CommintUtils.commonAlertDialog(ShowDataUI.this, "删除数据", "删除",
                        "确定", "取消", new AlertDialogI() {
                            @Override
                            public void sure(DialogInterface dialog, int which) {
                                dao.deleData(showDataList.get(position).data);
                                showDataList = dao.getData(dao.ALL_DATA);
                                adapter.refresh(showDataList);
                                Toast.makeText(ShowDataUI.this, "已删除",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(ShowDataUI.this, UpdataDataUI.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", showDataList.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            showDataList = dao.getData(dao.ALL_DATA);
            adapter.refresh(showDataList);
        }
    }

    ;

}
