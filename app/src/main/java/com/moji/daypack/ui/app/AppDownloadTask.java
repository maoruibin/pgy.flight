package com.moji.daypack.ui.app;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import rx.Observable;
import rx.Subscriber;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 8/22/16
 * Time: 12:12 AM
 * Desc: ObservableDownloadTask
 */

/* package */ class AppDownloadTask {

    private static final String TAG = "AppDownloadTask";
    private static final int BUFFER_SIZE = 16 * 1024;

    private String mUrl;
    private DownloadInfo mDownloadInfo;

    /* package */ AppDownloadTask(String url,File apkFile) {
        mUrl = url;
        mDownloadInfo = new DownloadInfo(apkFile);
    }

    /* package */ Observable<DownloadInfo> downloadApk() {
        return Observable.create(new Observable.OnSubscribe<DownloadInfo>() {
            @Override
            public void call(Subscriber<? super DownloadInfo> subscriber) {
                // Initiate download progress
                subscriber.onNext(mDownloadInfo);
                HttpURLConnection urlConnection = null;
                BufferedOutputStream outputStream = null;
                try {
                    URL url = new URL(mUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setInstanceFollowRedirects(true);
                    urlConnection.connect();
                    int contentLength = urlConnection.getContentLength();

                    Log.d(TAG, "Start downloading " + urlConnection.getURL());
                    Log.d(TAG, String.format("File size %.2f kb", (float) contentLength / 1024));

//                    String fileName = getFileName(urlConnection);
                    outputStream = new BufferedOutputStream(new FileOutputStream(mDownloadInfo.apkFile));
                    Log.d(TAG, "Downloading apk into " + mDownloadInfo.apkFile.getPath());
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int length;
                    int totalLength = 0;
                    InputStream in = urlConnection.getInputStream();
                    while ((length = in.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, length);
                        totalLength += length;
                        mDownloadInfo.progress = (totalLength == 0f) ? 0f : (float) totalLength / (float) contentLength;
                        subscriber.onNext(mDownloadInfo);
                    }
                } catch (IOException e) {
                    Log.e(TAG, String.format("Download: %s, %s", mDownloadInfo.apkFile, mUrl), e);
                    subscriber.onError(e);
                } finally {
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
                    }
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
                subscriber.onCompleted();
            }
        });
    }

    DownloadInfo getDownloadInfo() {
        return mDownloadInfo;
    }

    static class DownloadInfo {
        float progress;
        File apkFile;

        public DownloadInfo(File apkFile) {
            this.apkFile = apkFile;
        }
    }
}
