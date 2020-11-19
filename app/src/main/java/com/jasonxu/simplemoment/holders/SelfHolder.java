package com.jasonxu.simplemoment.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jasonxu.simplemoment.R;

/**
 * Author: jason xu
 * Time: 11/19/20 20:59
 */
public class SelfHolder extends RecyclerView.ViewHolder {

    public static final int LYT_RES = R.layout.self_item_lyt;

    public ImageView mBgIV;
    public ImageView mAvatarIV;
    public TextView mNameTV;

    public SelfHolder(@NonNull View itemView) {
        super(itemView);
    }
}
