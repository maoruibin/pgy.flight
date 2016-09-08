package io.github.ryanhoo.firFlight.ui.app;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import io.github.ryanhoo.firFlight.R;
import io.github.ryanhoo.firFlight.ui.base.BaseActivity;

public class AppDetailActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
        AppDetailFragment fragment = new AppDetailFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container,fragment)
                .commitAllowingStateLoss();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int colorDark = ContextCompat.getColor(this,R.color.blackBlueColorPrimaryDark);
            getWindow().setNavigationBarColor(colorDark);
        }
    }


}
