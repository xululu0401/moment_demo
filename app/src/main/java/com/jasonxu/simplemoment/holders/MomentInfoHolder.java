package com.jasonxu.simplemoment.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jasonxu.simplemoment.R;


/**
 * Author: jason xu
 * Time: 11/19/20 21:18
 */
public class MomentInfoHolder extends RecyclerView.ViewHolder {

    public static final int LYT_RES = R.layout.moment_item_lyt;

    public ImageView mAvatarIV;
    public TextView mNameTV;
    public TextView mContentTV;

    public MomentInfoHolder(@NonNull View itemView) {
        super(itemView);

        mAvatarIV = itemView.findViewById(R.id.avator_img);
        mNameTV = itemView.findViewById(R.id.name_tv);
        mContentTV = itemView.findViewById(R.id.content_tv);

    }
}
