package com.moji.daypack.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.moji.daypack.BuildConfig;
import com.moji.daypack.data.source.local.db.DatabaseHelper;
import com.moji.daypack.data.source.local.db.tables.BaseTable;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import rx.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 6/26/16
 * Time: 12:22 AM
 * Desc: AbstractLocalDataSource
 */
public abstract class AbstractLocalDataSource<T extends BaseTable> {

    protected T mTable;
    protected BriteDatabase mDatabaseHelper;

    public AbstractLocalDataSource(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SqlBrite sqlBrite = SqlBrite.create();
        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(databaseHelper, Schedulers.io());
        mDatabaseHelper.setLoggingEnabled(BuildConfig.DEBUG);
        mTable = instantiateTable();
    }

    @NonNull
    protected abstract T instantiateTable();
}
