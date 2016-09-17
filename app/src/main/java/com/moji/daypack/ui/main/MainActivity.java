package com.moji.daypack.ui.main;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.moji.daypack.R;
import com.moji.daypack.ui.about.AboutActivity;
import com.moji.daypack.ui.app.AppsFragment;
import com.moji.daypack.ui.base.BaseActivity;
import com.moji.daypack.ui.setting.SettingsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 7/8/16
 * Time: 9:28 PM
 * Desc: MainActivityV2
 */
public class MainActivity extends BaseActivity {

    private static final int REQUEST_CODE_ASK_WRITE_EXTERNAL_STORAGE = 0;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fl_container)
    FrameLayout flContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        checkPermission();
        ActionBar actionBar = supportActionBar(toolbar);
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container, AppsFragment.getInstance()).commitAllowingStateLoss();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE_ASK_WRITE_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_WRITE_EXTERNAL_STORAGE:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    AlertDialog dialog = new AlertDialog.Builder(this)
                            .setTitle("确定不授予存储权限吗？")
                            .setMessage("存储权限将用于存储 APK 文件的正常进行。")
                            .setPositiveButton(R.string.ff_grant, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    checkPermission();
                                }
                            })
                            .setNegativeButton(R.string.ff_refuse, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setCancelable(false)
                            .create();
                    dialog.show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_settings:
                onOpenSettings();
                break;
            case R.id.menu_item_about:
                onOpenAbout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onOpenSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private void onOpenAbout() {
        startActivity(new Intent(this, AboutActivity.class));
    }
}
