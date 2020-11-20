package com.jasonxu.simplemoment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.jasonxu.simplemoment.data.MomentInfo;
import com.jasonxu.simplemoment.data.SelfInfo;
import com.jasonxu.simplemoment.mvp.IDataView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IDataView {

    private RecyclerView mRv;
    private LinearLayoutManager mManager;
    private MomentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRv = findViewById(R.id.rv);
        mManager = new LinearLayoutManager(this);
        mRv.setLayoutManager(mManager);
    }

    @Override
    public void showData(SelfInfo info, List<MomentInfo> momentInfos) {
        if (null == mAdapter){
            mAdapter = new MomentAdapter(this, info, momentInfos);
            mRv.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
}