package io.github.ryanhoo.firFlight.ui.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.ryanhoo.firFlight.R;
import io.github.ryanhoo.firFlight.ui.base.BaseFragment;
import io.github.ryanhoo.firFlight.ui.common.DefaultItemDecoration;
import io.github.ryanhoo.firFlight.util.IntentUtils;

/**
 * Created by GuDong on 9/8/16 23:42.
 * Contact with gudong.name@gmail.com.
 */
public abstract class BaseAppListFragment<P extends AbsAppListContract.Presenter>
        extends BaseFragment
        implements AppAdapter.AppItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        AbsAppListContract.View<P>{
    protected P mPresenter;
    SwipeRefreshLayout swipeRefreshLayout;

    private static final String TAG = "BaseAppListFragment";
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    LinearLayoutManager layoutManager;
    AppAdapter mAdapter;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Listen for app install/update/remove broadcasts
        registerBroadcast();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        if(swipeRefreshLayout!=null){
            swipeRefreshLayout.setOnRefreshListener(this);
        }
        setUpAdapter();
    }

    private void setUpAdapter(){
        mAdapter = new AppAdapter(getActivity(), null, !isIndexAppList());
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DefaultItemDecoration(
                ContextCompat.getColor(getContext(), R.color.ff_white),
                ContextCompat.getColor(getContext(), R.color.ff_divider),
                getContext().getResources().getDimensionPixelSize(R.dimen.ff_padding_large)
        ));
    }

    /**首页 App List 界面
     * 是否是
     * @return
     */
    protected abstract boolean isIndexAppList();

    @Override
    public void onDestroy() {
        // Done with listening app install/update/remove broadcasts
        unregisterBroadcast();
        super.onDestroy();
    }

    // Broadcasts
    private void registerBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        getActivity().registerReceiver(receiver, intentFilter);
    }

    private void unregisterBroadcast() {
        getActivity().unregisterReceiver(receiver);
    }



    // Update app

    protected void updateItemView(final String appId, final int position) {
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

    @Override
    public void onLoadAppCompleted() {
        if(swipeRefreshLayout!=null){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onAppListLoaded(List apps) {
        mAdapter.setData(apps);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadAppStarted() {
        if(swipeRefreshLayout!=null){
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
        }
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
        IntentUtils.install(getActivity(), apkUri);
    }

    @Override
    public void setPresenter(P presenter) {
        mPresenter = presenter;
    }
}
