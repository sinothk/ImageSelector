package com.sinothk.image.selector.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.sinothk.image.selector.PhotoPickerActivity;
import com.sinothk.image.selector.SelectModel;
import com.sinothk.image.selector.intent.PhotoPickerIntent;

import java.io.File;
import java.util.ArrayList;

public class ImageSelectorSingleDemoMainActivity extends AppCompatActivity {

    int REQUEST_SINGLE_CODE = 1;

    ImageView imgIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_image_selector_single_demo);

        imgIv = findViewById(R.id.imgIv);

        findViewById(R.id.singleSelectedBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(ImageSelectorSingleDemoMainActivity.this);
                intent.setSelectModel(SelectModel.SINGLE);
                intent.setSelectedPaths(path);
                intent.setShowCamera(true, "com.sinothk.image.selector.demo"); // 是否显示拍照， 默认false

                startActivityForResult(intent, REQUEST_SINGLE_CODE);
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
                    Glide.with(this).load(new File(path.get(0))).placeholder(R.drawable.add_img).into(imgIv);
                }
            }
//            else if (requestCode == REQUEST_MUTILATE_CODE) {
//                // 图片选择结果展示
//                path = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
//                mAlbumSelectedShowAdapter.setData(path);
//            }
        }
    }


}
