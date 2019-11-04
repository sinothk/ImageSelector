package com.sinothk.image.show;

import android.view.View;

import androidx.annotation.LayoutRes;

import java.util.ArrayList;

/**
 * Created by jianhaohong on 5/9/16.
 */
public abstract class NineGridAdapter<T> {

    public abstract int getCount();

    public abstract int getItemLayoutRes(@LayoutRes int position);

    public abstract void onBindItemView(int position, View view);

    public abstract T getItem(int position);

    public abstract void setData(ArrayList<String> path);
}
