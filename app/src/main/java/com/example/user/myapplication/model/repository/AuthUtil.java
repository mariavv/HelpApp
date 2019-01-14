package com.example.user.myapplication.model.repository;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.user.myapplication.R;
import com.example.user.myapplication.model.entity.Token;
import com.example.user.myapplication.model.entity.UserCredentials;
import com.example.user.myapplication.model.remote.OAuth;
import com.example.user.myapplication.model.remote.RestService;
import com.example.user.myapplication.model.remote.RestServiceProvider;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AuthUtil {

    private static final String TOKEN_KEY = "token";
    private static final String TOKEN_TYPE_KEY = "token_type";

    private static final String USER_NAME = "user1";
    private static final String PASSWORD = "123456";

    private static final String UNAUTHORIZED = "Unauthorized";
    private static final int DEFOULT_ERR_COUNT = 0;
    private static final int MAX_ERR_COUNT = 1;

    private static int authErrorCount = DEFOULT_ERR_COUNT;

    private Context context;
    private Listener listener;

    public AuthUtil(Context context) {
        this.context = context;
    }

    public interface Listener {
        void onSetToken();
        void onAuthError(String message);
        void onAuthError(int messageRes);
    }

    public void loadToken(Context context) {
        OAuth.setToken(SaveDataHelper.getValue(TOKEN_TYPE_KEY, context), SaveDataHelper.getValue(TOKEN_KEY, context));
    }

    void checkAuth(Throwable throwable, Listener listener) {
        this.listener = listener;
        String message = throwable.getMessage();
        if (isUnauthorized(message)) {
            if (++authErrorCount <= MAX_ERR_COUNT) {
                authorize(context);
            } else {
                authErrorCount = DEFOULT_ERR_COUNT;
            }
        } else {
            listener.onAuthError(message);
        }
    }

    private void saveToken(Token token, Context context) {
        SaveDataHelper.saveValue(TOKEN_TYPE_KEY, token.getTokenType(), context);
        SaveDataHelper.saveValue(TOKEN_KEY, token.getAccessToken(), context);
    }

    @SuppressLint("CheckResult")
    private void authorize(Context context) {
        getToken()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(token -> onGetToken(token, context), throwable -> listener.onAuthError(throwable.getMessage()));
    }

    private Observable<Token> getToken() {
        RestService restService = RestServiceProvider
                .getInstance(RestServiceProvider.Target.AUTH)
                .getRestService();
        return restService.getToken(new UserCredentials(USER_NAME, PASSWORD));
    }

    private void onGetToken(Token token, Context context) {
        if (OAuth.setToken(token)) {
            saveToken(token, context);
            listener.onSetToken();
        } else {
            listener.onAuthError(R.string.error_get_auth_token);
        }
    }

    private boolean isUnauthorized(String message) {
        return message.contains(UNAUTHORIZED);
    }
}
