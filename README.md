
# 引入
  ## Step 1. Add the JitPack repository to your build file
    Add it in your root build.gradle at the end of repositories:

      allprojects {
        repositories {
          ...
          maven { url 'https://jitpack.io' }
        }
      }
      
  ## Step 2. Add the dependency

    dependencies {
            implementation 'com.github.sinothk:ImageSelector:3.0.0329'
    }

# 使用
  ## 预览：
      PhotoPreviewActivity.start(Activity mActivity, int position, ArrayList<String> urlOrFilePathList,int    PhotoPreviewActivity.REQUEST_PREVIEW);
  
  ## 图片选择：
    单图：
        PhotoPickerIntent intent = new PhotoPickerIntent(ImageSelectorDemoMainActivity.this);
        intent.setSelectModel(SelectModel.SINGLE);
        intent.setSelectedPaths(path);
        intent.setShowCamera(true, "com.sinothk.image.selector.demo"); // 是否显示拍照， 默认false

        startActivityForResult(intent, REQUEST_SINGLE_CODE);
        
    多图：
        PhotoPickerIntent intent = new PhotoPickerIntent(ImageSelectorDemoMainActivity.this);
        intent.setSelectModel(SelectModel.MULTI);
        intent.setSelectedPaths(path);
        intent.setMaxTotal(9);
        intent.setShowCamera(true, "com.sinothk.image.selector.demo"); // 是否显示拍照， 默认false

        startActivityForResult(intent, REQUEST_MUTILATE_CODE);
        
    返回：onActivityResult(int requestCode, int resultCode, Intent data)
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
                         PhotoPreviewActivity.start(ImageSelectorDemoMainActivity.this, position, urlOrFilePathList); // 不要预览删除
                    
                        PhotoPreviewActivity.start(ImageSelectorDemoMainActivity.this, position, urlOrFilePathList, PhotoPreviewActivity.REQUEST_PREVIEW);

                    }
                });
                
                // 图片选择结果展示
                path = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                mAlbumSelectedShowAdapter.setData(path);
            }
        }

# 选后展示：
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
