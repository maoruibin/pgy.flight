package com.moji.daypack.ui.app;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moji.daypack.R;
import com.moji.daypack.data.model.IAppBasic;
import com.moji.daypack.data.source.AppRepository;
import com.moji.daypack.network.ServerConfig;
import com.moji.daypack.ui.common.widget.CharacterDrawable;
import com.moji.daypack.util.AppUtils;
import com.moji.daypack.util.IntentUtils;
import com.moji.daypack.webview.WebViewHelper;

import java.io.File;
import java.util.List;

import butterknife.Bind;

/**
 * Created by GuDong on 9/8/16 23:44.
 * Contact with gudong.name@gmail.com.
 */
public class AppDetailFragment extends BaseAppListFragment<String,AppDetailContract.Presenter<String>> implements AppDetailContract.View<String>{
    public static final String KEY_APP = "APP_KEY";
    public static final String KEY_APP_NAME = "APP_NAME";
    public static final String KEY_APP_TYPE = "APP_TYPE";
    public static final String KEY_APP_ICON = "APP_ICON";
    public static final String KEY_APP_IDENTIFIER = "APP_IDENTIFIER";

    public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.parallax_background)
    LinearLayout mBackground;
    @Bind(R.id.collapsing_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @Bind(R.id.image_view_icon)
    ImageView mAppIcon;
    @Bind(R.id.app_bar)
    AppBarLayout appBarLayout;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.text_view_app_type)
    TextView mTvAppType;

    private Menu mMenu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_app_detail_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        final String appName = getActivity().getIntent().getStringExtra(KEY_APP_NAME);
        mToolbarLayout.setTitle(appName);
        mTvName.setText(appName);


        String appIcon = getActivity().getIntent().getStringExtra(KEY_APP_ICON);
        Glide.with(this)
                .load(appIcon)
                .placeholder(CharacterDrawable.create(getActivity(), appName.charAt(0), false, R.dimen.ff_padding_large))
                .into(mAppIcon);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(mAppIcon, VIEW_NAME_HEADER_IMAGE);
        }

        String appType = getActivity().getIntent().getStringExtra(KEY_APP_TYPE);
        boolean isAndroidApp = AppUtils.isAndroidApp(appType);
        mTvAppType.setText(isAndroidApp?"Android":"iOS");
        mTvAppType.setBackgroundResource(isAndroidApp?R.drawable.type_android:R.drawable.type_ios);
    }

    @Override
    AppDetailContract.Presenter instancePresenter() {
        return new AppDetailPresenter(AppRepository.getInstance(),this);
    }

    @Override
    String subscribeParam() {
        return getActivity().getIntent().getStringExtra(KEY_APP);
    }

    @Override
    protected boolean isIndexAppList() {
        return false;
    }

    @Override
    public void onItemClick(View view, int position) {
        IAppBasic app = mAdapter.getItem(position);
        WebViewHelper.openUrl(getActivity(), ServerConfig.APP_DETAIL_HOST.concat(app.getAppKey()));
    }

    @Override
    public void onButtonClick(AppItemView itemView, int position) {
        final AppInfo appInfo = itemView.appInfo;

        if (appInfo != null) {
            final String packageName = appInfo.app.getAppIdentifier();
            if (AppUtils.isAppInstalled(getActivity(), packageName)) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("卸载提示")
                        .setMessage("检测到你已经安装了该 App，如果要覆盖安装，请先卸载" + appInfo.app.getAppName())
                        .setPositiveButton(R.string.ff_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AppUtils.uninstallApp(getActivity(), packageName);
                            }
                        })
                        .setNegativeButton(R.string.ff_cancel, null)
                        .show();
            } else {
                IAppBasic app = appInfo.app;
                final File installedFile = AppUtils.getInstalledApkFile(app);
                if (installedFile.exists() && installedFile.getTotalSpace() > 0) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("安装提示")
                            .setMessage("检测到该安装包已下载，是否直接安装？\n\n路径："+installedFile.getPath())
                            .setPositiveButton(R.string.ff_install, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    IntentUtils.install(getActivity(), installedFile);
                                }
                            })
                            .setNegativeButton(R.string.ff_cancel, null)
                            .setNeutralButton("删除并重新下载", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(installedFile.delete()){
                                        IntentUtils.install(getActivity(), installedFile);
                                    }
                                }
                            })
                            .show();

                } else {
                    mPresenter.requestInstallUrl(itemView, position);
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail,menu);
        mMenu = menu;
        updateMenu(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void updateMenu(Menu menu) {
        if(mPresenter.checkAppInstallStatus()){
            menu.findItem(R.id.menu_item_uninstall).setVisible(true);
        } else {
            menu.findItem(R.id.menu_item_uninstall).setVisible(false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_uninstall:
                mPresenter.uninstall();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    protected void onReceiverBroadcast() {
        super.onReceiverBroadcast();
        updateMenu(mMenu);
    }

    @Override
    public void onAppListLoaded(List apps) {
        super.onAppListLoaded(apps);
        updateMenu(mMenu);
    }
}
