package com.moji.daypack.ui.setting;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.moji.daypack.R;
import com.moji.daypack.RxBus;
import com.moji.daypack.event.FilterIosEvent;
import com.moji.daypack.ui.base.BaseActivity;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 4/3/16
 * Time: 5:10 PM
 * Desc: SettingsActivity
 */
public class SettingsActivity extends BaseActivity {
    private static File DOWNLOAD_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        supportActionBar(toolbar);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fl_container, new SettingsFragment())
                    .commit();
        }
    }

    public static class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

        @Override public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting);
            findPreference("preference_clear_cache").setOnPreferenceClickListener(this);
            findPreference("preference_clear_download").setOnPreferenceClickListener(this);
            findPreference("preference_filter_ios").setOnPreferenceChangeListener(this);
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            switch (preference.getKey()){
                case "preference_clear_cache":
                    clearImageCache();
                    break;
                case "preference_clear_download":
                    new AlertDialog.Builder(getActivity())
                            .setTitle("提示")
                            .setMessage("确定要清空所有下载的 APK 文件吗？")
                            .setPositiveButton(R.string.ff_confirm, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    File[]list = DOWNLOAD_DIR.listFiles();
                                    for(File file:list){
                                        if(file.getName().endsWith(".apk")){
                                            if(file.getName().contains("墨迹") || file.getName().contains("日拱一卒") || file.getName().contains("Android")){
                                                file.deleteOnExit();
                                            }
                                        }
                                    }

                                }
                            })
                            .setNegativeButton(R.string.ff_cancel,null)
                            .show();

                    break;
            }
            return false;
        }

        private void clearImageCache() {
            new AsyncTask<Void, Integer, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    // Must NOT call this on UI thread
                    Glide.get(getActivity()).clearDiskCache();
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    // Must call this on UI thread
                    Glide.get(getActivity()).clearMemory();
                    showSnake(Snackbar.make(getView(),
                            R.string.ff_settings_message_cache_cleared, Snackbar.LENGTH_LONG));
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        private void showSnake(final Snackbar snackbar) {
            snackbar.setAction(R.string.ff_dismiss, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            RxBus.getInstance().post(new FilterIosEvent());
            return true;
        }
    }
}
