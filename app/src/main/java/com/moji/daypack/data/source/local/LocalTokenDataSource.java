package com.moji.daypack.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.moji.daypack.data.model.Token;
import com.moji.daypack.data.source.TokenContract;
import com.moji.daypack.data.source.local.db.tables.TokenTable;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 7/3/16
 * Time: 9:39 PM
 * Desc: LocalTokenDataSource
 */
public class LocalTokenDataSource extends AbstractLocalDataSource<TokenTable> implements TokenContract.Local {

    public LocalTokenDataSource(Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected TokenTable instantiateTable() {
        return new TokenTable();
    }

    @Override
    public Token token() {
        Cursor cursor = mDatabaseHelper.query(TokenTable.QUERY_TOKEN);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            return mTable.parseCursor(cursor);
        }
        return null;
    }

    @Override
    public boolean save(Token token) {
        return mDatabaseHelper.insert(TokenTable.TABLE_NAME, mTable.toContentValues(token), SQLiteDatabase.CONFLICT_REPLACE) == 1;
    }

    @Override
    public boolean delete(Token token) {
        return mDatabaseHelper.delete(TokenTable.TABLE_NAME, TokenTable.WHERE_ACCESS_TOKEN_EQUALS, token.getAccessToken()) == 1;
    }

    @Override
    public boolean deleteAll() {
        return mDatabaseHelper.delete(TokenTable.TABLE_NAME, null) == 1;
    }
}
