package com.jasonxu.simplemoment.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jasonxu.simplemoment.R;

/**
 * Author: jason xu
 * Time: 11/19/20 21:39
 */
public class FooterHolder extends RecyclerView.ViewHolder {

    public static final int LYT_RES = R.layout.load_item_lyt;
    public TextView mLoadTV;


    public FooterHolder(@NonNull View itemView) {
        super(itemView);
        mLoadTV = itemView.findViewById(R.id.load_tv);
    }
}
