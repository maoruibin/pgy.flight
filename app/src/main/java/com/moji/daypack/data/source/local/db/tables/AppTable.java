package com.moji.daypack.data.source.local.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;

import com.moji.daypack.data.model.AppEntity;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 6/1/16
 * Time: 12:59 PM
 * Desc: AppTable
 */
public final class AppTable implements BaseColumns, BaseTable<AppEntity> {

    private static final String TAG = "AppTable";

    // Table Name
    public static final String TABLE_NAME = "app";

    // Columns
    public static final String COLUMN_APP_KEY = "appKey"; // "appKey"
    public static final String COLUMN_USER_KEY = "userKey";
    public static final String COLUMN_APP_NAME = "appName";
    public static final String COLUMN_ICON_URL = "appIcon";
    public static final String COLUMN_APP_VERSION = "appVersion";
    public static final String COLUMN_APP_IDENTIFIER = "appIdentifier";
    public static final String COLUMN_CUSTOM_MARKET_URL = "custom_market_url";
    public static final String COLUMN_APP_CREATED = "appCreated";
    public static final String COLUMN_APP_TYPE = "appType";
    public static final String COLUMN_RELEASE_ID = "release_id";
    public static final String COLUMN_APP_BUILD_VERSION = "appBuildVersion";
    public static final String COLUMN_APP_FILE_SIZE = "appFileSize";
    public static final String COLUMN_APP_UPDATE_DESCRIPTION = "appUpdateDescription";


    // Create & Delete
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME +
                " ( " +
                    COLUMN_APP_KEY + " TEXT PRIMARY KEY UNIQUE, " +
                    COLUMN_USER_KEY + " TEXT, " +
                    COLUMN_APP_NAME + " TEXT, " +
                    COLUMN_APP_VERSION + " TEXT, " +
                    COLUMN_APP_IDENTIFIER + " TEXT, " +
                    COLUMN_CUSTOM_MARKET_URL + " TEXT, " +
                    COLUMN_APP_CREATED + " INTEGER, " +
                    COLUMN_ICON_URL + " TEXT , " +
                    COLUMN_APP_TYPE + " TEXT , " +
                    COLUMN_RELEASE_ID + " INTEGER , " +
                    COLUMN_APP_FILE_SIZE + " INTEGER , " +
                    COLUMN_APP_BUILD_VERSION + " TEXT , " +
                    COLUMN_APP_UPDATE_DESCRIPTION + " TEXT" +
                    // The fucking foreign key doesn't work, replacing it with trigger
                    // "FOREIGN KEY(" + COLUMN_RELEASE_ID + ") REFERENCES " +
                    // ReleaseTable.TABLE_NAME + "(" + ReleaseTable._ID + ") ON DELETE CASCADE" +
                " );";

    public static final String DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    // Delete the related Release when app is being deleted
    public static final String DELETE_APP_TRIGGER =
            "CREATE TRIGGER IF NOT EXISTS delete_app_trigger AFTER DELETE ON " + AppTable.TABLE_NAME + "\n" +
                    "FOR EACH ROW\n" +
                    "BEGIN\n" +
                    "DELETE FROM " + ReleaseTable.TABLE_NAME + " " +
                    "WHERE " + ReleaseTable._ID + " = old." + AppTable.COLUMN_RELEASE_ID + ";\n" +
                    "END";

    public static final String QUERY_ALL_APPS = "SELECT * FROM " + TABLE_NAME + ";";

    public static final String WHERE_ID_EQUALS = COLUMN_APP_KEY + "=?";

    @Override
    public String createTableSql() {
        return CREATE_TABLE;
    }

    @Override
    public String deleteTableSql() {
        return DELETE_TABLE;
    }

    @Override
    public ContentValues toContentValues(AppEntity app) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_APP_KEY, app.getAppKey());
        contentValues.put(COLUMN_USER_KEY, app.userKey);
        contentValues.put(COLUMN_APP_NAME, app.getAppName());
        contentValues.put(COLUMN_APP_IDENTIFIER, app.getAppIdentifier());
        contentValues.put(COLUMN_APP_VERSION, app.getAppVersion());
        contentValues.put(COLUMN_APP_BUILD_VERSION, app.getAppBuildVersion());
        contentValues.put(COLUMN_ICON_URL, app.getAppIcon());
        contentValues.put(COLUMN_CUSTOM_MARKET_URL,"");
        contentValues.put(COLUMN_APP_TYPE, app.getAppType());
        contentValues.put(COLUMN_APP_CREATED, app.appCreated);
        contentValues.put(COLUMN_APP_FILE_SIZE, app.getAppFileSize());

        contentValues.put(COLUMN_APP_UPDATE_DESCRIPTION, app.getAppUpdateDescription());
        return contentValues;
    }

    @Override
    public AppEntity parseCursor(Cursor c) {

        AppEntity app = new AppEntity();
        app.setAppKey(c.getString(c.getColumnIndexOrThrow(COLUMN_APP_KEY)));
        app.setUserKey(c.getString(c.getColumnIndexOrThrow(COLUMN_USER_KEY)));
        app.setAppName(c.getString(c.getColumnIndexOrThrow(COLUMN_APP_NAME)));
        app.setAppIdentifier(c.getString(c.getColumnIndexOrThrow(COLUMN_APP_IDENTIFIER)));
        app.setAppVersion(c.getString(c.getColumnIndexOrThrow(COLUMN_APP_VERSION)));
        app.setAppBuildVersion(c.getString(c.getColumnIndexOrThrow(COLUMN_APP_BUILD_VERSION)));
        app.setAppIcon(c.getString(c.getColumnIndexOrThrow(COLUMN_ICON_URL)));
        app.setAppType(c.getString(c.getColumnIndexOrThrow(COLUMN_APP_TYPE)));
        app.setAppCreated(c.getString(c.getColumnIndexOrThrow(COLUMN_APP_CREATED)));
        app.setAppFileSize(c.getInt(c.getColumnIndexOrThrow(COLUMN_APP_FILE_SIZE)));
        app.setAppUpdateDescription(c.getString(c.getColumnIndexOrThrow(COLUMN_APP_UPDATE_DESCRIPTION)));
        Log.i("====",app.toString());
        return app;
    }
}
