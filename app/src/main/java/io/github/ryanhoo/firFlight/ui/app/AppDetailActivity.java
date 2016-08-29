package io.github.ryanhoo.firFlight.ui.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.ryanhoo.firFlight.R;
import io.github.ryanhoo.firFlight.data.model.AppDetailModel;
import io.github.ryanhoo.firFlight.data.model.IAppBasic;
import io.github.ryanhoo.firFlight.data.source.AppRepository;
import io.github.ryanhoo.firFlight.ui.base.BaseActivity;
import io.github.ryanhoo.firFlight.ui.common.DefaultItemDecoration;
import io.github.ryanhoo.firFlight.ui.helper.SwipeRefreshHelper;
import io.github.ryanhoo.firFlight.util.AppUtils;
import io.github.ryanhoo.firFlight.util.IntentUtils;

public class AppDetailActivity extends BaseActivity implements AppDetailContract.View, SwipeRefreshLayout.OnRefreshListener, AppAdapter.AppItemClickListener {
    public static final String KEY_APP = "APP_KEY";
    public static final String KEY_APP_NAME = "APP_NAME";
    public static final String KEY_APP_TYPE = "APP_TYPE";
    private static final String TAG = "AppDetailActivity";
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    @Bind(R.id.empty_view)
    View emptyView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    AppAdapter mAdapter;
    AppDetailContract.Presenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
        ButterKnife.bind(this);


        supportActionBar(toolbar);

        SwipeRefreshHelper.setRefreshIndicatorColorScheme(swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new AppAdapter(this, null);
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DefaultItemDecoration(
                ContextCompat.getColor(getContext(), R.color.ff_white),
                ContextCompat.getColor(getContext(), R.color.ff_divider),
                getContext().getResources().getDimensionPixelSize(R.dimen.ff_padding_large)
        ));

        new AppDetailPresenter(AppRepository.getInstance(), this).subscribe();

        String appKey = getIntent().getStringExtra(KEY_APP);
        mPresenter.loadAppDetail(appKey);

        toolbar.setOverflowIcon(ContextCompat.getDrawable(this,R.drawable.ic_more_vert_black_24dp));
        String appName = getIntent().getStringExtra(KEY_APP_NAME);
        toolbar.setTitle(appName);

        // Listen for app install/update/remove broadcasts
        registerBroadcast();
    }

    @Override
    protected void onDestroy() {
        // Done with listening app install/update/remove broadcasts
        unregisterBroadcast();
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onFillAppDetail(AppDetailModel detail) {

    }

    @Override
    public void onFillHistList(List<IAppBasic> apps) {
        Log.i("====","apps is "+apps.size());
        mAdapter.setData(apps);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onLoadAppStarted() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void onLoadAppCompleted() {
        swipeRefreshLayout.setRefreshing(false);
        boolean isEmpty = mAdapter.getItemCount() == 0;
        emptyView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }

    @Override
    public void addTask(String appId, AppDownloadTask task) {
        mAdapter.addTask(appId, task);
    }

    @Override
    public void removeTask(String appId) {
        mAdapter.removeTask(appId);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateAppInfo(String appId, int position) {
        updateItemView(appId, position);
    }

    @Override
    public void installApk(Uri apkUri) {
        IntentUtils.install(this, apkUri);
    }


    @Override
    public void setPresenter(AppDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onRefresh() {
        String appKey = getIntent().getStringExtra(KEY_APP);
        mPresenter.loadAppDetail(appKey);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onButtonClick(AppItemView itemView, int position) {
        final AppInfo appInfo = itemView.appInfo;
        if (appInfo != null) {
            if (appInfo.isUpToDate) {
                startActivity(appInfo.launchIntent);
            } else {
                mPresenter.requestInstallUrl(itemView, position);
//                mPresenter.appView(appInfo.app.appKey);
                //WebViewHelper.openUrl(getActivity(),appInfo.app.appName,"https://www.pgyer.com/"+appInfo.app.appKey);
            }
        }
    }

    // Update app

    private void updateItemView(final String appId, final int position) {
        // Only update view holder if it's visible on the screen
        int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
        int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

        if (position < firstVisibleItem || position > lastVisibleItem) return;

        // Get the view holder by position
        View itemView = layoutManager.getChildAt(position - firstVisibleItem);

        if (itemView instanceof AppItemView) {
            AppItemView appView = (AppItemView) itemView;
            if (appView.appInfo == null) return;
            if (appView.appInfo.app == null || !appId.equals(appView.appInfo.app.getAppKey())) return;

            mAdapter.onButtonProgress(appView);
        }
    }

    // Broadcasts

    private void registerBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        registerReceiver(receiver, intentFilter);
    }

    private void unregisterBroadcast() {
        unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: " + intent.getAction());
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String appType = getIntent().getStringExtra(KEY_APP_TYPE);
         menu.findItem(R.id.menu_item_uninstall).setVisible(AppUtils.isAndroidApp(appType));
        return super.onPrepareOptionsMenu(menu);
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
}
