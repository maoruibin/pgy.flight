package com.moji.daypack.ui.app;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moji.daypack.R;
import com.moji.daypack.data.model.IAppBasic;
import com.moji.daypack.ui.common.adapter.IAdapterView;
import com.moji.daypack.ui.common.widget.CharacterDrawable;
import com.moji.daypack.util.AppUtils;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 8/21/16
 * Time: 10:46 PM
 * Desc: AppItemView
 */

public class AppItemView extends RelativeLayout implements IAdapterView<IAppBasic> {

    Context mContext;
    @Bind(R.id.image_view_icon)
    ImageView imageView;
    @Bind(R.id.text_view_name)
    TextView textViewName;
    @Bind(R.id.text_view_local_version)
    TextView textViewLocalVersion;
    @Bind(R.id.text_view_version)
    TextView textViewVersion;
    @Bind(R.id.text_view_app_type)
    TextView textViewAppType;
    @Bind(R.id.et_change_log)
    ExpandableTextView textViewChangeLog;
    @Bind(R.id.button_action)
    Button buttonAction;
    @Bind(R.id.text_view_tip_update)
    TextView textViewTipUpdate;
    @Bind(R.id.text_view_app_size)
    TextView textViewAppSize;
    @Bind(R.id.text_view_update_time)
    TextView textViewUpdateTime;
    @Bind(R.id.ll_app_name)
    LinearLayout llAppNameLayout;


    AppInfo appInfo;
    private boolean isShowInAppDetail;
    public AppItemView(Context context) {
        super(context);

        mContext = context;
        View.inflate(context, R.layout.item_app, this);
        ButterKnife.bind(this);

        textViewLocalVersion.setPaintFlags(textViewLocalVersion.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public AppItemView(Context context,boolean isShowInAppDetail) {
        this(context);
        this.isShowInAppDetail = isShowInAppDetail;
    }

    @Override
    public void bind(IAppBasic app, int position) {
        appInfo = new AppInfo(mContext, app);
        if(isShowInAppDetail){
            imageView.setVisibility(GONE);
            llAppNameLayout.setVisibility(GONE);
            textViewChangeLog.setMaxCollapsedLines(4);
        }

        if(imageView.getVisibility() == VISIBLE){
            Glide.with(mContext)
                    .load(app.getAppIcon())
                    .placeholder(CharacterDrawable.create(mContext, app.getAppName().charAt(0), false, R.dimen.ff_padding_large))
                    .into(imageView);
        }

        textViewName.setText(app.getAppName());
        textViewVersion.setText(String.format("%s(%s)",
                app.getAppVersion(),
                app.getAppBuildVersion()
        ));


        // Hide old version when app is not installed or already up-to-date
        textViewLocalVersion.setVisibility(
                (appInfo.isUpToDate || !appInfo.isInstalled) ? View.GONE : View.VISIBLE);
        textViewTipUpdate.setVisibility(
                (appInfo.isUpToDate || !appInfo.isInstalled) ? View.GONE : View.VISIBLE);
        if (appInfo.isInstalled && !appInfo.isUpToDate) {
            textViewLocalVersion.setText(String.format("%s(%s)",
                    appInfo.localVersionName, appInfo.localVersionCode));
        }
        boolean isAndroidApp = AppUtils.isAndroidApp(app.getAppType());
        buttonAction.setVisibility(isAndroidApp ? View.VISIBLE : View.GONE);
        textViewAppType.setText(isAndroidApp?"Android":"iOS");
        textViewAppType.setBackgroundResource(isAndroidApp? R.drawable.type_android: R.drawable.type_ios);
        if(!TextUtils.isEmpty(app.getAppUpdateDescription())){
            textViewChangeLog.setText(app.getAppUpdateDescription());
        }else{
            textViewChangeLog.setText("咦？开发者没有提交任何日志！");
        }

        DecimalFormat decimalFormat=new DecimalFormat("0.00");
        float size = (float)app.getAppFileSize()/(1024*1024);
        String stringSize = decimalFormat.format(size);
        textViewAppSize.setText(stringSize+"Mb");

        textViewUpdateTime.setText("更新于"+app.getAppCreated());
    }
}
