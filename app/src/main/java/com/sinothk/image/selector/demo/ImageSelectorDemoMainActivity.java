package com.sinothk.image.selector.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.sinothk.image.selector.PhotoPickerActivity;
import com.sinothk.image.selector.PhotoPreviewActivity;
import com.sinothk.image.selector.SelectModel;
import com.sinothk.image.selector.intent.PhotoPickerIntent;
import com.sinothk.image.show.AppNineGridAdapter;
import com.sinothk.image.show.NineGridView;

import java.util.ArrayList;

public class ImageSelectorDemoMainActivity extends AppCompatActivity {
    int REQUEST_SINGLE_CODE = 1;// 从相册选择
    int REQUEST_MUTILATE_CODE = 2;// 从相册选择

    TextView result1, result2;
    AppNineGridAdapter nineGridAdapter;
    NineGridView nineGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_image_selector_demo);

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

        findViewById(R.id.mutilateSelectedBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(ImageSelectorDemoMainActivity.this);
                intent.setSelectModel(SelectModel.MULTI);
                intent.setSelectedPaths(path);
                intent.setMaxTotal(9);
                intent.setShowCamera(true, "com.sinothk.image.selector.demo"); // 是否显示拍照， 默认false

                startActivityForResult(intent, REQUEST_MUTILATE_CODE);
            }
        });

        nineGridView = findViewById(R.id.nine_grid_view);
    }

    ArrayList<String> path = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //图片选择返回
            if (requestCode == REQUEST_SINGLE_CODE) {
                path = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                if (path != null) {
                    StringBuilder s = new StringBuilder();
                    for (int i = 0; i < path.size(); i++) {
                        s.append(path.get(i));
                    }
                    result1.setText(s.toString());
                }
            } else if (requestCode == REQUEST_MUTILATE_CODE) {
                path = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);

                nineGridAdapter = new AppNineGridAdapter(ImageSelectorDemoMainActivity.this, path);
                nineGridView.setNineGridAdapter(nineGridAdapter);

                nineGridAdapter.setOnItemClickListener(new AppNineGridAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int position, ArrayList<String> urlOrFilePathList) {
                        PhotoPreviewActivity.start(ImageSelectorDemoMainActivity.this, position, urlOrFilePathList, PhotoPreviewActivity.REQUEST_PREVIEW);

                    }
                });
            }
        }
    }
}
