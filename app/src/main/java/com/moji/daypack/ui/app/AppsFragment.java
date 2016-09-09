package com.moji.daypack.ui.app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moji.daypack.R;
import com.moji.daypack.RxBus;
import com.moji.daypack.data.model.IAppBasic;
import com.moji.daypack.data.source.AppRepository;
import com.moji.daypack.event.FilterIosEvent;
import com.moji.daypack.ui.helper.SwipeRefreshHelper;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 3/19/16
 * Time: 12:29 AM
 * Desc: AppListFragment
 */
public class AppsFragment extends BaseAppListFragment<Void,AppContract.Presenter<Void>> implements AppContract.View<Void>{

    @Bind(R.id.empty_view)
    View emptyView;

    public static AppsFragment getInstance(){
        AppsFragment appsFragment = new AppsFragment();
        Bundle data = new Bundle();
        appsFragment.setArguments(data);
        return appsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_app_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SwipeRefreshHelper.setRefreshIndicatorColorScheme(swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        RxBus.getInstance()
                .toObservable()
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        if(o instanceof FilterIosEvent){
                            mPresenter.loadAppList();
                        }
                    }
                });
    }

    @Override
    AppContract.Presenter instancePresenter() {
        return new AppPresenter(AppRepository.getInstance(),this);
    }

    @Override
    Void subscribeParam() {
        return null;
    }

    @Override
    protected boolean isIndexAppList() {
        return true;
    }


    // AppItemClickListener

    @Override
    public void onItemClick(View view, int position) {
        IAppBasic app = mAdapter.getItem(position);
        Intent intent = new Intent(getActivity(),AppDetailActivity.class);
        intent.putExtra(AppDetailFragment.KEY_APP,app.getAppKey());
        intent.putExtra(AppDetailFragment.KEY_APP_NAME,app.getAppName());
        intent.putExtra(AppDetailFragment.KEY_APP_TYPE,app.getAppType());
        intent.putExtra(AppDetailFragment.KEY_APP_ICON,app.getAppIcon());
        intent.putExtra(AppDetailFragment.KEY_APP_IDENTIFIER,app.getAppIdentifier());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View target = view.findViewById(R.id.image_view_icon);
            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), new Pair<View, String>(target,AppDetailFragment.VIEW_NAME_HEADER_IMAGE));
            ActivityCompat.startActivity(getActivity(), intent, activityOptions.toBundle());
        }else{
            startActivity(intent);
        }
    }

    @Override
    public void onButtonClick(final AppItemView itemView, final int position) {
        final AppInfo appInfo = itemView.appInfo;
        if (appInfo != null) {
            if (appInfo.isUpToDate) {
                startActivity(appInfo.launchIntent);
            } else {
                mPresenter.requestInstallUrl(itemView, position);
            }
        }
    }


    @Override
    public void onRefresh() {
        mPresenter.loadAppList();
    }

    @Override
    public void onLoadAppCompleted() {
        super.onLoadAppCompleted();
        boolean isEmpty = mAdapter.getItemCount() == 0;
        emptyView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }
}
