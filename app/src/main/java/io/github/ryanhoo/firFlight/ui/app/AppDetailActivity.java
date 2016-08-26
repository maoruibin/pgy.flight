package io.github.ryanhoo.firFlight.ui.app;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.ryanhoo.firFlight.R;
import io.github.ryanhoo.firFlight.data.model.AppEntity;
import io.github.ryanhoo.firFlight.data.model.AppPgy;
import io.github.ryanhoo.firFlight.data.source.AppRepository;
import io.github.ryanhoo.firFlight.ui.base.BaseActivity;
import io.github.ryanhoo.firFlight.ui.common.DefaultItemDecoration;
import io.github.ryanhoo.firFlight.ui.common.adapter.OnItemClickListener;
import io.github.ryanhoo.firFlight.ui.helper.SwipeRefreshHelper;

public class AppDetailActivity extends BaseActivity implements AppDetailContract.View, SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {
    public static final String KEY_APP = "APP_KEY";
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    @Bind(R.id.empty_view)
    View emptyView;

    AppAdapter mAdapter;
    AppDetailContract.Presenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
        ButterKnife.bind(this);

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
        mPresenter.loadApps(appKey);
        // Listen for app install/update/remove broadcasts
        //registerBroadcast();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onAppsLoaded(List<AppEntity> apps) {
        Log.i("====","apps is "+apps.size());
    }

    @Override
    public void onLoadAppStarted() {

    }

    @Override
    public void onLoadAppCompleted() {

    }

    @Override
    public void setPresenter(AppDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onItemClick(int position) {

    }
}
