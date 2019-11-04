package com.sinothk.image.selector.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sinothk.image.selector.PhotoPreviewActivity;
import com.sinothk.image.show.AppNineGridAdapter;
import com.sinothk.image.show.NineGridView;

import java.util.ArrayList;

public class ImageListShowDemoActivity extends AppCompatActivity {

    AppNineGridAdapter nineGridAdapter;
    NineGridView nineGridView;

    ArrayList<String> pathList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_image_list_show);

        pathList = getIntent().getStringArrayListExtra("pathList");

        nineGridView = this.findViewById(R.id.nineGridView);

        nineGridAdapter = new AppNineGridAdapter(ImageListShowDemoActivity.this, pathList);
        nineGridView.setNineGridAdapter(nineGridAdapter);

        nineGridAdapter.setOnItemClickListener(new AppNineGridAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position, ArrayList<String> urlOrFilePathList) {
                PhotoPreviewActivity.start(ImageListShowDemoActivity.this, position, urlOrFilePathList);
            }
        });
    }
}
