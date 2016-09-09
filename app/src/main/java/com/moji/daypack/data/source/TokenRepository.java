package com.moji.daypack.data.source;

import com.moji.daypack.data.Injection;
import com.moji.daypack.data.model.Token;
import com.moji.daypack.data.source.local.LocalTokenDataSource;
import com.moji.daypack.data.source.remote.RemoteTokenDataSource;
import rx.Observable;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 7/3/16
 * Time: 9:54 PM
 * Desc: TokenRepository
 */
public class TokenRepository implements TokenContract {

    private static TokenRepository sInstance;

    TokenContract.Local mLocalDataSource;
    TokenContract.Remote mRemoteDataSource;

    private TokenRepository() {
        mLocalDataSource = new LocalTokenDataSource(Injection.provideContext());
        mRemoteDataSource = new RemoteTokenDataSource(Injection.provideRESTfulApi());
    }

    public static TokenRepository getInstance() {
        if (sInstance == null) {
            synchronized (AppRepository.class) {
                if (sInstance == null) {
                    sInstance = new TokenRepository();
                }
            }
        }
        return sInstance;
    }

    @Override
    public boolean storeToken(Token token) {
        mLocalDataSource.deleteAll();
        return mLocalDataSource.save(token);
    }

    @Override
    public Token restoreToken() {
        return mLocalDataSource.token();
    }

    @Override
    public Observable<Token> accessToken(String email, String password) {
        return mRemoteDataSource.accessToken(email, password);
    }

    @Override
    public Observable<Token> apiToken() {
        return mRemoteDataSource.apiToken();
    }

    @Override
    public Observable<Token> refreshApiToken() {
        return mRemoteDataSource.refreshApiToken();
    }
}
