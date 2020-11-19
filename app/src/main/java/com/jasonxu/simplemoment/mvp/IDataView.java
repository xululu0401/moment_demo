package com.jasonxu.simplemoment.mvp;

import com.jasonxu.simplemoment.data.MomentInfo;
import com.jasonxu.simplemoment.data.SelfInfo;

import java.util.List;


/**
 * Author: jason xu
 * Time: 11/19/20 21:46
 */
public interface IDataView {

    void showData(SelfInfo info, List<MomentInfo> momentInfos);

}
