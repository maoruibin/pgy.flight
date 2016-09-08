package io.github.ryanhoo.firFlight.ui.app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
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

import java.io.File;

import butterknife.Bind;
import io.github.ryanhoo.firFlight.R;
import io.github.ryanhoo.firFlight.data.model.IAppBasic;
import io.github.ryanhoo.firFlight.data.source.AppRepository;
import io.github.ryanhoo.firFlight.ui.common.widget.CharacterDrawable;
import io.github.ryanhoo.firFlight.util.AppUtils;
import io.github.ryanhoo.firFlight.util.IntentUtils;

/**
 * Created by GuDong on 9/8/16 23:44.
 * Contact with gudong.name@gmail.com.
 */
public class AppDetailFragment extends BaseAppListFragment<AppDetailContract.Presenter> implements AppDetailContract.View{
    public static final String KEY_APP = "APP_KEY";
    public static final String KEY_APP_NAME = "APP_NAME";
    public static final String KEY_APP_TYPE = "APP_TYPE";
    public static final String KEY_APP_ICON = "APP_ICON";
    public static final String KEY_APP_IDENTIFIER = "APP_IDENTIFIER";

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

        new AppDetailPresenter(AppRepository.getInstance(), this).subscribe();

        String appKey = getActivity().getIntent().getStringExtra(KEY_APP);
        mPresenter.loadAppDetail(appKey);


        final String appName = getActivity().getIntent().getStringExtra(KEY_APP_NAME);
        mToolbarLayout.setTitle(appName);
        mTvName.setText(appName);


        String appIcon = getActivity().getIntent().getStringExtra(KEY_APP_ICON);
        Glide.with(this)
                .load(appIcon)
                .placeholder(CharacterDrawable.create(getActivity(), appName.charAt(0), false, R.dimen.ff_padding_large))
                .into(mAppIcon);

        int color = ContextCompat.getColor(getActivity(), R.color.blackBlueColorPrimary);
        int colorDark = ContextCompat.getColor(getActivity(), R.color.blackBlueColorPrimaryDark);

        mBackground.setBackgroundColor(color);
        mToolbarLayout.setContentScrimColor(color);
        mToolbarLayout.setStatusBarScrimColor(colorDark);
    }

    @Override
    protected boolean isIndexAppList() {
        return false;
    }

    @Override
    public void onItemClick(int position) {

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
                            .setMessage("检测到该安装包已下载，是否直接安装？")
                            .setPositiveButton(R.string.ff_ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    IntentUtils.install(getActivity(), installedFile);
                                }
                            })
                            .setNegativeButton(R.string.ff_cancel, null)
                            .show();

                } else {
                    mPresenter.requestInstallUrl(itemView, position);
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        String appType = getActivity().getIntent().getStringExtra(KEY_APP_TYPE);
        String appPackage = getActivity().getIntent().getStringExtra(KEY_APP_IDENTIFIER);
        if (AppUtils.isAndroidApp(appType) && AppUtils.isAppInstalled(getActivity(), appPackage)) {
            menu.findItem(R.id.menu_item_uninstall).setVisible(true);
        } else {
            menu.findItem(R.id.menu_item_uninstall).setVisible(false);
        }
        inflater.inflate(R.menu.detail, menu);
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
}
