package com.moji.daypack.ui.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.moji.daypack.R;
import com.moji.daypack.data.model.IAppBasic;
import com.moji.daypack.ui.common.adapter.ListAdapter;
import com.moji.daypack.ui.common.adapter.OnItemClickListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 8/21/16
 * Time: 10:48 PM
 * Desc: AppAdapterV2
 */
class AppAdapter extends ListAdapter<IAppBasic, AppItemView> {

    private Map<String, AppDownloadTask> mTasks;
    private AppItemClickListener mItemClickListener;
    private boolean isShowInAppDetail;
    AppAdapter(Context context, List<IAppBasic> data) {
        super(context, data);
        mTasks = new HashMap<>();
    }

    AppAdapter(Context context, List<IAppBasic> data,boolean isShowInAppDetail) {
        this(context, data);
        this.isShowInAppDetail = isShowInAppDetail;
    }

    @Override
    protected AppItemView createView(Context context) {
        return new AppItemView(context,isShowInAppDetail);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerView.ViewHolder holder = super.onCreateViewHolder(parent, viewType);
        if (holder.itemView instanceof AppItemView && mItemClickListener != null) {
            final AppItemView itemView = (AppItemView) holder.itemView;
            itemView.buttonAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onButtonClick(itemView, holder.getAdapterPosition());
                }
            });
        }
        return holder;
    }

    @Override
    @SuppressLint("DefaultLocale")
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder.itemView instanceof AppItemView) {
            IAppBasic app = getItem(position);
            AppItemView itemView = (AppItemView) holder.itemView;

            if (mTasks.containsKey(app.getAppKey())) {
                AppDownloadTask.DownloadInfo downloadInfo = mTasks.get(app.getAppKey()).getDownloadInfo();
                // Has downloading task, show progress
                itemView.buttonAction.setText(String.format("%d%%", (int) (downloadInfo.progress * 100)));
                itemView.buttonAction.setEnabled(false);
            } else {
                itemView.buttonAction.setEnabled(true);
                itemView.buttonAction.setText(!itemView.appInfo.isInstalled
                        ? R.string.ff_apps_install
                        : itemView.appInfo.isUpToDate ? R.string.ff_apps_open : R.string.ff_apps_update
                );
                if(isShowInAppDetail){
                    itemView.buttonAction.setText(R.string.ff_apps_install);
                }
            }
        }
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        super.setOnItemClickListener(listener);
        if (listener instanceof AppItemClickListener) {
            mItemClickListener = (AppItemClickListener) listener;
        }
    }

    @SuppressLint("DefaultLocale")
    /* package */ void onButtonProgress(@NonNull AppItemView appItemView) {
        IAppBasic app = appItemView.appInfo.app;
        if (mTasks.containsKey(app.getAppKey())) {
            AppDownloadTask.DownloadInfo downloadInfo = mTasks.get(app.getAppKey()).getDownloadInfo();
            appItemView.buttonAction.setText(String.format("%d%%", (int) (downloadInfo.progress * 100)));
        }
    }

    /* package */ void addTask(String appId, AppDownloadTask task) {
        mTasks.put(appId, task);
    }

    /* package */ void removeTask(String appId) {
        mTasks.remove(appId);
    }

    /* package */ Map<String, AppDownloadTask> getTasks() {
        return mTasks;
    }

    interface AppItemClickListener extends OnItemClickListener {
        void onButtonClick(AppItemView itemView, int position);
    }
}
