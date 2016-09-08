package io.github.ryanhoo.firFlight.ui.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import io.github.ryanhoo.firFlight.R;
import io.github.ryanhoo.firFlight.ui.base.BaseFragment;

/**
 * Created by GuDong on 2016/8/26 10:34.
 * Contact with ruibin.mao@moji.com.
 */
public class TodayFragment extends BaseFragment {
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    @Bind(R.id.empty_view)
    View emptyView;

    AppAdapter mAdapter;

    AppContract.Presenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_app_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
