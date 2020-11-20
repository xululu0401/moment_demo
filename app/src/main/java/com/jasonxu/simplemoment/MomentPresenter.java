package com.jasonxu.simplemoment;

import com.jasonxu.simplemoment.data.MomentInfo;
import com.jasonxu.simplemoment.data.SelfInfo;
import com.jasonxu.simplemoment.mvp.IDataPresenter;
import com.jasonxu.simplemoment.mvp.IDataView;
import com.jasonxu.simplemoment.util.DataGetExecutor;

import java.util.List;

public class MomentPresenter implements IDataPresenter {

    private IDataView mView;
    private MomentModel mModel;
    private MomentModel.IDataFecthListener mDataListener = new MomentModel.IDataFecthListener() {
        @Override
        public void onDataFetched(SelfInfo selfInfo, List<MomentInfo> momentInfos) {
            if (null != mView) {
                mView.showData(selfInfo, momentInfos);
            }
        }
    };


    public MomentPresenter(IDataView view){
        mView = view;
        mModel = new MomentModel();
        mModel.setDataCallback(mDataListener);
    }


    @Override
    public void loadData() {
        DataGetExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mModel.requestData();
            }
        });
    }
}
