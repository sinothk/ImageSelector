
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
            implementation 'com.github.sinothk:ImageSelector:2.1.0321'
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
        
    返回：
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
