package com.jasonxu.simplemoment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jasonxu.simplemoment.data.Constants;
import com.jasonxu.simplemoment.data.MomentInfo;
import com.jasonxu.simplemoment.data.SelfInfo;
import com.jasonxu.simplemoment.mvp.IDataModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MomentModel implements IDataModel {

    public interface IDataFecthListener{
        void onDataFetched(SelfInfo selfInfo, List<MomentInfo> momentInfos);
    }

    private IDataFecthListener mListener;
    private List<MomentInfo> mAllMoment;
    private SelfInfo mSelf;
    private int mCurIndex;

    public void setDataCallback(IDataFecthListener listener){
        mListener = listener;
    }

    @Override
    public void requestData() {
        if (null == mAllMoment) {
            getMomentInfo();
        }
        if (mSelf == null) {
            getSelfInfo();
        }
        if (null != mListener){
            mCurIndex =5;
            mListener.onDataFetched(mSelf, mAllMoment.subList(0, 5));
        }
    }

    public void requestMore() {
        mCurIndex = mCurIndex + 5 > mAllMoment.size() ? mAllMoment.size() : mCurIndex + 5;

        mListener.onDataFetched(mSelf, mAllMoment.subList(0, mCurIndex));
    }

    /**
     * 真实场景下，该信息应该是登陆后存在本地
     * @return
     */
    private void getSelfInfo(){
        Gson gson = new Gson();
        mSelf = gson.fromJson(Constants.SELF_INFO, SelfInfo.class);
    }

    private void getMomentInfo(){
        Type type =new TypeToken<ArrayList<MomentInfo>>(){}.getType();
        Gson gson = new Gson();
        mAllMoment = gson.fromJson(Constants.MOMENT_INFO, type);
    }
}
