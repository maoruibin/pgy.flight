package com.moji.daypack.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.moji.daypack.data.source.UserContract;
import com.moji.daypack.data.source.local.db.tables.UserTable;
import com.moji.daypack.data.model.User;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 7/2/16
 * Time: 10:34 PM
 * Desc: LocalUserDataSource
 */
public class LocalUserDataSource extends AbstractLocalDataSource<UserTable> implements UserContract.Local {

    public LocalUserDataSource(Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected UserTable instantiateTable() {
        return new UserTable();
    }

    @Override
    public Observable<User> user() {
        return mDatabaseHelper.createQuery(UserTable.TABLE_NAME, UserTable.QUERY_USER)
                .mapToOne(new Func1<Cursor, User>() {
                    @Override
                    public User call(Cursor cursor) {
                        if (cursor.getCount() > 0) {
                            return mTable.parseCursor(cursor);
                        }
                        return null;
                    }
                });
    }

    @Override
    public User _user() {
        Cursor cursor = mDatabaseHelper.query(UserTable.QUERY_USER);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            return mTable.parseCursor(cursor);
        }
        return null;
    }

    @Override
    public boolean save(User user) {
        return mDatabaseHelper.insert(UserTable.TABLE_NAME, mTable.toContentValues(user), SQLiteDatabase.CONFLICT_REPLACE) == 1;
    }

    @Override
    public boolean delete(User user) {
        return mDatabaseHelper.delete(UserTable.TABLE_NAME, UserTable.WHERE_ID_EQUALS, user.getId()) == 1;
    }

    @Override
    public int deleteAll() {
        return mDatabaseHelper.delete(UserTable.TABLE_NAME, null);
    }
}
