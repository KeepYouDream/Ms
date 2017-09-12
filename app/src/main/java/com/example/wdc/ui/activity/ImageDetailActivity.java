package com.example.wdc.ui.activity;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.wdc.bean.images.ImagesListBean;
import com.example.wdc.ms.R;
import com.example.wdc.ui.activity.base.BaseActivity;
import com.example.wdc.ui.activity.base.BaseAppCompatActivity;
import com.example.wdc.ui.fragment.ImagesFragment;
import com.example.wdc.utils.NetUtils;
import com.example.wdc.widgets.SmoothImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by wdc on 2016/7/26.
 */
public class ImageDetailActivity extends BaseAppCompatActivity {

    @BindView(R.id.imgdetail_smoothimgview)
    protected SmoothImageView mSmoothImageView;
    @BindView(R.id.imagedetail_root)
    protected LinearLayout imagedetail_root;

    private int x;
    private int y;
    private int width;
    private int height;
    private String url;

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (extras != null){
            url = extras.getString(ImagesFragment.KEY_IMG_URL);
            x = extras.getInt(ImagesFragment.KEY_IMG_X);
            y = extras.getInt(ImagesFragment.KEY_IMG_Y);
            width = extras.getInt(ImagesFragment.KEY_IMG_WIDTH);
            height = extras.getInt(ImagesFragment.KEY_IMG_HEIGHT);
        }
        setTheme(R.style.DefaultTheme_ImageDetailTheme);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_imgdetail;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }
    @Override
    protected void initViewsAndEvents() {
        imagedetail_root.setBackgroundResource(android.R.color.transparent);
        mSmoothImageView.setOriginalInfo(width, height, x, y);
        mSmoothImageView.transformIn();
        ImageLoader.getInstance().displayImage(url, mSmoothImageView);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.black));

        //        Glide.with(this).load(url)
//                .placeholder(drawable)
//                .into(mSmoothImageView);

        mSmoothImageView.setOnTransformListener(new SmoothImageView.TransformListener() {
            @Override
            public void onTransformComplete(int mode) {
                if (mode == 2) {
                    finish();
                }
            }
        });
        mSmoothImageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                mSmoothImageView.transformOut();
            }
        });
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    protected Boolean isAddStatusHeight() {
        return false;
    }

    @Override
    public void onBackPressed() {
        mSmoothImageView.transformOut();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(0, 0);
        }
    }
}
