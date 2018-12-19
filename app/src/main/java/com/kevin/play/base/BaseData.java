package com.kevin.play.base;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

/**
 * Created by Kevin on 2018/12/19<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public interface BaseData {
    @LayoutRes
    int getLayoutResource();

    int getItemViewType();

    BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    void onBindViewHolder(BaseViewHolder holder, int position);
}
