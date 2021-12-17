# traveller 一个用于生产前的技术准备库

### 一、 module-basic
封装base 基类的库
1. BaseActivity.class  Activity的基类
2. BaseFragment.class Fragment 的基类
3. BaseRecyclerViewAdapter.class  RecyclerViewAdapter  的基类
  
  使用前需要在 build.gradle中设置 
```
buildFeatures {
        viewBinding true
    }
```

### 二、module-camerax
一个提供相机拍照、录制视频的库，兼容Android 10、11、12。

### 三、 module-sdk 
封装了rxjava、retrofit、lifecycle的库，用于网络请求。

### 四、module-widget
一个封装和自定义view的库。

