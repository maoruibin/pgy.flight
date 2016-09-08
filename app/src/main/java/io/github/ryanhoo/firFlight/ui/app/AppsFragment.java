package io.github.ryanhoo.firFlight.ui.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.ryanhoo.firFlight.R;
import io.github.ryanhoo.firFlight.data.model.IAppBasic;
import io.github.ryanhoo.firFlight.data.source.AppRepository;
import io.github.ryanhoo.firFlight.ui.helper.SwipeRefreshHelper;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 3/19/16
 * Time: 12:29 AM
 * Desc: AppListFragment
 */
public class AppsFragment extends BaseAppListFragment<AppContract.Presenter> implements AppContract.View{

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
        ButterKnife.bind(this, view);

        SwipeRefreshHelper.setRefreshIndicatorColorScheme(swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        new AppPresenter(AppRepository.getInstance(), this).subscribe();

    }

    @Override
    protected boolean isIndexAppList() {
        return true;
    }


    // AppItemClickListener

    @Override
    public void onItemClick(int position) {
        IAppBasic app = mAdapter.getItem(position);
        Intent intent = new Intent(getActivity(),AppDetailActivity.class);
        intent.putExtra(AppDetailFragment.KEY_APP,app.getAppKey());
        intent.putExtra(AppDetailFragment.KEY_APP_NAME,app.getAppName());
        intent.putExtra(AppDetailFragment.KEY_APP_TYPE,app.getAppType());
        intent.putExtra(AppDetailFragment.KEY_APP_ICON,app.getAppIcon());
        intent.putExtra(AppDetailFragment.KEY_APP_IDENTIFIER,app.getAppIdentifier());
        startActivity(intent);
    }

    @Override
    public void onButtonClick(final AppItemView itemView, final int position) {
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
