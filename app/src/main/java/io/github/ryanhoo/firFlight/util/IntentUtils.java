package io.github.ryanhoo.firFlight.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 3/20/16
 * Time: 9:12 PM
 * Desc: IntentUtils
 */
public class IntentUtils {

    public static void install(Context context, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public static void install(Context context, File file) {
        install(context, Uri.fromFile(file));
    }
}
