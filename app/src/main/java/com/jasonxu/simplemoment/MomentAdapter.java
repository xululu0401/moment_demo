package com.jasonxu.simplemoment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jasonxu.simplemoment.data.CommentInfo;
import com.jasonxu.simplemoment.data.MomentInfo;
import com.jasonxu.simplemoment.data.SelfInfo;
import com.jasonxu.simplemoment.holders.FooterHolder;
import com.jasonxu.simplemoment.holders.MomentInfoHolder;
import com.jasonxu.simplemoment.holders.SelfHolder;

import java.util.List;

/**
 * Author: jason xu
 * Time: 11/19/20 20:38
 */
public class MomentAdapter extends RecyclerView.Adapter {

    private static final int SELF_TYPE = 0;
    private static final int MOMENT_TYPE = 1;
    private static final int FOOTER_TYPE = 2;

    private Context mContext;
    private List<MomentInfo> mMomentInfos;
    private SelfInfo mSelfInfo;

    public MomentAdapter(Context ctx, SelfInfo selfInfo, List<MomentInfo> momentInfos){
        mContext = ctx;
        mSelfInfo = selfInfo;
        momentInfos = momentInfos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == SELF_TYPE){
            return new SelfHolder(LayoutInflater.from(mContext).inflate(SelfHolder.LYT_RES, null));
        } else if (viewType == MOMENT_TYPE){
            return new MomentInfoHolder(LayoutInflater.from(mContext).inflate(MomentInfoHolder.LYT_RES, null));
        } else {
            return new FooterHolder(LayoutInflater.from(mContext).inflate(FooterHolder.LYT_RES, null));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == SELF_TYPE){

        } else if (viewType == MOMENT_TYPE){

        } else {

        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return SELF_TYPE;
        } else if (position == mMomentInfos.size()){
            return FOOTER_TYPE;
        } else {
            return MOMENT_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return mMomentInfos.size() + 2;
    }
}
