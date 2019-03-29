package com.sinothk.image.selector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.sinothk.image.selector.widget.ViewPagerFixed;

import java.util.ArrayList;

/**
 * 类描述：
 * 创建时间：2017/8/21 0021 上午 0:29
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

/**
 * 使用PhotoPreviewActivity代替
 */
@Deprecated
public class ImagePreviewActivity extends AppCompatActivity implements PhotoPagerAdapter.PhotoViewClickListener {

    public static final String EXTRA_PHOTOS = "extra_photos";
    public static final String EXTRA_CURRENT_ITEM = "extra_current_item";

    /**
     * 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合
     */
    public static final String EXTRA_RESULT = "preview_result";


    private ArrayList<String> paths;
    private ViewPagerFixed mViewPager;
    private PhotoPagerAdapter mPagerAdapter;
    private int currentItem = 0;
    private TextView titleBarTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ms_activity_image_preview);

        initViews();

        paths = new ArrayList<>();
        ArrayList<String> pathArr = getIntent().getStringArrayListExtra(EXTRA_PHOTOS);

//        ArrayList<String> pathArr = new ArrayList<>();
//        pathArr.add("http://p0.so.qhimgs1.com/t016122ec45c5fdc5fa.jpg");
//        pathArr.add("http://p5.so.qhimgs1.com/t01e0413153ed24f8db.jpg");
//        pathArr.add("http://p0.so.qhimgs1.com/t014110342128f441a0.jpg");

        if (pathArr != null) {
            paths.addAll(pathArr);
        }

        currentItem = getIntent().getIntExtra(EXTRA_CURRENT_ITEM, 0);

        mPagerAdapter = new PhotoPagerAdapter(this, paths);
        mPagerAdapter.setPhotoViewClickListener(this);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(currentItem);
        mViewPager.setOffscreenPageLimit(5);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                updateActionBarTitle();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        updateActionBarTitle();
    }

    private void initViews() {
        mViewPager = (ViewPagerFixed) findViewById(R.id.vp_photos);


        titleBarTxt = (TextView) findViewById(R.id.titleBarTxt);

        RelativeLayout titleBarLeft = (RelativeLayout) findViewById(R.id.titleBarLeft);
        titleBarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        TextView completeTv = (TextView) findViewById(R.id.completeTv);
        completeTv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void OnPhotoTapListener(View view, float v, float v1) {
        onBackPressed();
    }

    public void updateActionBarTitle() {
        String titleStr = getString(R.string.image_index, mViewPager.getCurrentItem() + 1, paths.size());
        titleBarTxt.setText(titleStr);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_RESULT, paths);
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//
        //返回
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
