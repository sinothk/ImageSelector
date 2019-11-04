package com.sinothk.image.selector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.sinothk.android.utils.StatusBarUtil;
import com.sinothk.image.selector.widget.ViewPagerFixed;

import java.util.ArrayList;

/**
 * Created by foamtrace on 2015/8/25.
 */
public class PhotoPreviewActivity extends AppCompatActivity implements PhotoPagerAdapter.PhotoViewClickListener {

    private TextView completeTv;
    private TextView titleBarTxt;

    public static final String EXTRA_PHOTOS = "extra_photos";
    public static final String EXTRA_CURRENT_ITEM = "extra_current_item";

    public static final String EXTRA_NEED_DELETE = "EXTRA_NEED_DELETE";
    /**
     * 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合
     */
    public static final String EXTRA_RESULT = "preview_result";

    /**
     * 预览请求状态码
     */
    public static final int REQUEST_PREVIEW = 99;

    private boolean isNeedDelete = false;

    private ArrayList<String> paths;
    private ViewPagerFixed mViewPager;
    private PhotoPagerAdapter mPagerAdapter;
    private int currentItem = 0;

    public static void start(Activity mActivity, int position, ArrayList<String> urlOrFilePathList) {
        Intent intent = new Intent(mActivity, PhotoPreviewActivity.class);
        intent.putExtra(EXTRA_CURRENT_ITEM, position);
        intent.putExtra(EXTRA_NEED_DELETE, false);
        intent.putStringArrayListExtra(EXTRA_PHOTOS, urlOrFilePathList);
        mActivity.startActivity(intent);
    }

    public static void start(Activity mActivity, int position, ArrayList<String> urlOrFilePathList, int requestPreview) {
        Intent intent = new Intent(mActivity, PhotoPreviewActivity.class);
        intent.putExtra(EXTRA_CURRENT_ITEM, position);
        intent.putStringArrayListExtra(EXTRA_PHOTOS, urlOrFilePathList);
        intent.putExtra(EXTRA_NEED_DELETE, true);
        mActivity.startActivityForResult(intent, requestPreview);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ms_activity_image_preview);
        StatusBarUtil.transparencyBar(this);

        isNeedDelete = getIntent().getBooleanExtra(EXTRA_NEED_DELETE, false);
        currentItem = getIntent().getIntExtra(EXTRA_CURRENT_ITEM, 0);
        ArrayList<String> pathArr = getIntent().getStringArrayListExtra(EXTRA_PHOTOS);

        initViews();
        paths = new ArrayList<>();

        if (pathArr != null) {
            paths.addAll(pathArr);
        }

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

//        Toolbar mToolbar = (Toolbar) findViewById(R.id.pickerToolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RelativeLayout titleBarLeft = (RelativeLayout) findViewById(R.id.titleBarLeft);
        titleBarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView backTv = (TextView) findViewById(R.id.backTv);

        titleBarTxt = (TextView) findViewById(R.id.titleBarTxt);
        titleBarTxt.setText("预览");

        completeTv = (TextView) findViewById(R.id.completeTv);
        if (!isNeedDelete) {
            completeTv.setVisibility(View.GONE);
        } else {
            completeTv.setVisibility(View.VISIBLE);
            completeTv.setText("删除");
            completeTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 删除当前照片
                    final int index = mViewPager.getCurrentItem();
                    final String deletedPath = paths.get(index);
                    Snackbar snackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), R.string.deleted_a_photo,
                            Snackbar.LENGTH_LONG);

                    if (paths.size() <= 1) {

                        paths.remove(index);
                        onBackPressed();

                        Toast.makeText(getApplicationContext(), "已全部删除", Toast.LENGTH_SHORT).show();


//                    // 最后一张照片弹出删除提示
//                    // show confirm dialog
//                    new AlertDialog.Builder(getApplicationContext())
//                            .setTitle(R.string.confirm_to_delete)
//                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.dismiss();
//                                    paths.remove(index);
//                                    onBackPressed();
//                                }
//                            })
//                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.dismiss();
//                                }
//                            })
//                            .show();
                    } else {
                        snackbar.show();
                        paths.remove(index);
                        mPagerAdapter.notifyDataSetChanged();
                    }

                    snackbar.setAction(R.string.undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (paths.size() > 0) {
                                paths.add(index, deletedPath);
                            } else {
                                paths.add(deletedPath);
                            }
                            mPagerAdapter.notifyDataSetChanged();
                            mViewPager.setCurrentItem(index, true);
                        }
                    });
                }
            });
        }
    }

    @Override
    public void OnPhotoTapListener(View view, float v, float v1) {
        onBackPressed();
    }

    public void updateActionBarTitle() {
        titleBarTxt.setText(getString(R.string.image_index, mViewPager.getCurrentItem() + 1, paths.size()));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_RESULT, paths);
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_preview, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }
}
