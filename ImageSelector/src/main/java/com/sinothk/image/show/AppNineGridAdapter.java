package com.sinothk.image.show;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sinothk.image.selector.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  创建:  梁玉涛 2019/3/6 on 20:42
 *  项目:  ImageSelectorLib
 *  描述:
 *  更新:
 * <pre>
 */
public class AppNineGridAdapter extends NineGridAdapter<String> {

    private List<String> imageList;
    private Context context;

    public AppNineGridAdapter(Context context, List<String> imageList) {
        this.imageList = imageList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public int getItemLayoutRes(@LayoutRes int position) {
        if (imageList.size() == 1) {
            return R.layout.item_nine_view_big_image;
        } else {
            return R.layout.item_nine_view_normal;
        }
    }

    @Override
    public void onBindItemView(int position, View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        Glide.with(context).load(new File(getItem(position))).placeholder(R.color.colorAccent).into(imageView);
    }

    @Override
    public String getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public void setData(ArrayList<String> path) {
        imageList.clear();
        imageList.addAll(path);

    }
}
