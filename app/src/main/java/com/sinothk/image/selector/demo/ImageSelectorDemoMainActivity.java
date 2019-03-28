package com.sinothk.image.selector.demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sinothk.comm.utils.StatusBarUtil;
import com.sinothk.image.selected.ImageSelectedShowAdapter;
import com.sinothk.image.selector.PhotoPickerActivity;
import com.sinothk.image.selector.PhotoPreviewActivity;
import com.sinothk.image.selector.SelectModel;
import com.sinothk.image.selector.intent.PhotoPickerIntent;

import java.util.ArrayList;

public class ImageSelectorDemoMainActivity extends AppCompatActivity {
    int REQUEST_SINGLE_CODE = 1;// 从相册选择
    int REQUEST_MUTILATE_CODE = 2;// 从相册选择

    TextView result1, result2;
    RecyclerView mRvAlbumSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_image_selector_demo);
        StatusBarUtil.transparencyBar(this);

        result1 = (TextView) findViewById(R.id.result1);
        result2 = (TextView) findViewById(R.id.result2);

        findViewById(R.id.singleSelectedBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(ImageSelectorDemoMainActivity.this);
                intent.setSelectModel(SelectModel.SINGLE);
                intent.setSelectedPaths(path);
                intent.setShowCamera(true, "com.sinothk.image.selector.demo"); // 是否显示拍照， 默认false

                startActivityForResult(intent, REQUEST_SINGLE_CODE);
            }
        });

        setImg();

        findViewById(R.id.mutilateShowBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (path.size() == 0) {
                    return;
                }

                Intent intent = new Intent(ImageSelectorDemoMainActivity.this, ImageListShowDemoActivity.class);
                intent.putStringArrayListExtra("pathList", path);
                startActivity(intent);
            }
        });
    }

    ArrayList<String> path = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SINGLE_CODE) {

                // 单图
                path = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                if (path != null) {
                    StringBuilder s = new StringBuilder();
                    for (int i = 0; i < path.size(); i++) {
                        s.append(path.get(i));
                    }
                    result1.setText(s.toString());
                }
            } else if (requestCode == REQUEST_MUTILATE_CODE) {
                // 图片选择结果展示
                path = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                mAlbumSelectedShowAdapter.setData(path);
            }
        }
    }

    ImageSelectedShowAdapter mAlbumSelectedShowAdapter;

    private void setImg() {
        mRvAlbumSelected = this.findViewById(R.id.mRvAlbumSelected);

        mAlbumSelectedShowAdapter = new ImageSelectedShowAdapter(this, path, 6);
        mRvAlbumSelected.setLayoutManager(new GridLayoutManager(this, 4));
        mRvAlbumSelected.setHasFixedSize(true);
        mRvAlbumSelected.setAdapter(mAlbumSelectedShowAdapter);

        mAlbumSelectedShowAdapter.setOnItemClickListener(new ImageSelectedShowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (path.size() == position) {
                    //选择相册功能
                    PhotoPickerIntent intent = new PhotoPickerIntent(ImageSelectorDemoMainActivity.this);
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setSelectedPaths(path);
                    intent.setMaxTotal(6);
                    intent.setShowCamera(true, "com.sinothk.image.selector.demo"); // 是否显示拍照， 默认false

                    startActivityForResult(intent, REQUEST_MUTILATE_CODE);
                } else {
                    //图片展示界面  - path.size()
                    PhotoPreviewActivity.start(ImageSelectorDemoMainActivity.this, position, path);
                }
            }
        });

        mAlbumSelectedShowAdapter.setItemDelClick(new ImageSelectedShowAdapter.OnItemDelClickListener() {
            @Override
            public void onItemDelClick(int position) {
                dialog(position);
            }
        });
    }

    /*
     * Dialog对话框提示用户删除操作
     * position为删除图片位置
     */
    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认移除已添加图片吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                path.remove(position);
                mAlbumSelectedShowAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
