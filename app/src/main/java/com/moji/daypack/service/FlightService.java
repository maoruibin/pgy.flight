package com.moji.daypack.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.moji.daypack.R;
import com.moji.daypack.data.model.IAppBasic;
import com.moji.daypack.data.source.AppRepository;
import com.moji.daypack.ui.app.AppInfo;
import com.moji.daypack.ui.main.MainActivity;

import java.util.List;

import rx.Subscriber;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 3/23/16
 * Time: 8:35 PM
 * Desc: FlightService
 * - https://guides.codepath.com/android/Managing-Threads-and-Custom-Services#threading-within-the-service
 * - https://guides.codepath.com/android/Starting-Background-Services
 */
public class FlightService extends IntentService {

    private static final String TAG = "FlightService";

    NotificationManager notificationManager;

    public FlightService() {
        super("FlightService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: on thread #" + Thread.currentThread().getName());
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: " + intent.getAction() + " on thread #" + Thread.currentThread().getName());
        //if (!UserSession.getInstance().isSignedIn()) return;

        Log.d(TAG, "Start requesting apps...");
        AppRepository.getInstance().apps(true)
                .subscribe(new Subscriber<List<IAppBasic>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Request apps completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Request apps: ", e);

                    }

                    @Override
                    public void onNext(List<IAppBasic> apps) {
                        for (final IAppBasic app : apps) {
                            AppInfo appInfo = new AppInfo(FlightService.this, app);
                            if (appInfo.isInstalled && !appInfo.isUpToDate) {
                                onAppNewVersion(app);
                            }
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Destroy Update Service");
        super.onDestroy();
    }

    private void onAppNewVersion(IAppBasic app) {
        // Notification
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        String title = app.getAppName();
        String content = getString(R.string.ff_notification_app_new_version_message,
                app.getAppVersion(), app.getAppBuildVersion());
        Log.d(TAG, String.format("%s \t %s", title, content));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setColor(Color.parseColor("#42000000"));
        }
        Notification notification = builder.build();
        int notificationId = app.getAppKey().hashCode();
        notificationManager.notify(notificationId, notification);
    }
}
