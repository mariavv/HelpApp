package com.example.user.myapplication.model.remote;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.user.myapplication.model.Const.EMPTY_STRING;
import static com.example.user.myapplication.model.Const.ENCODING;
import static com.example.user.myapplication.model.StringUtil.nonNull;
import static com.example.user.myapplication.model.remote.Const.RequestHeader.AUTH_TYPE_VALUE;

public class RestServiceProvider {

    private static final int UPPER_IND = 0;
    private static final int LOWER_IND = 1;

    private static final RestServiceProvider INSTANCE = new RestServiceProvider();

    private static final int MIN_VALUE = 100;
    private static final int RND_VALUE = 899;

    private static String tokenType = OAuth.DEF_VALUE;
    private static String token = OAuth.DEF_VALUE;

    private RestService restService;

    public enum Target {
        AUTH,
        TABLES
    }

    public static RestServiceProvider getInstance(Target target) {
        switch (target) {
            case TABLES:
                tokenType = nonNull(OAuth.getTypeToken());
                if (tokenType.length() > LOWER_IND) {
                    tokenType = tokenType.substring(UPPER_IND, LOWER_IND).toUpperCase() + tokenType.substring(LOWER_IND).toLowerCase();
                }
                token = nonNull(OAuth.getToken());
                break;
            case AUTH:
                tokenType = AUTH_TYPE_VALUE;
                try {
                    token = base64Secret();
                } catch (UnsupportedEncodingException e) {
                    token = OAuth.DEF_VALUE;
                    e.printStackTrace();
                }
                break;
            default:
                tokenType = OAuth.DEF_VALUE;
                token = OAuth.DEF_VALUE;
                break;
        }
        return INSTANCE;
    }

    public synchronized final RestService getRestService() {
        if (restService == null) {
            restService = createRestService();
        }
        return restService;
    }

    private static String base64Secret() throws UnsupportedEncodingException {
        int rnd = MIN_VALUE + new Random(System.currentTimeMillis()).nextInt(RND_VALUE);
        // нужно минимум 3 знака
        byte[] data = (String.valueOf(rnd) + Const.SECRET).getBytes(ENCODING);
        return Base64.encodeToString(data, Base64.DEFAULT).replace(Const.ENTER, EMPTY_STRING);
    }

    private RestService createRestService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestService.API_URL)
                .client(provideClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(RestService.class);
    }

    private OkHttpClient provideClient() {
        Interceptor authInterceptor = chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header(Const.RequestHeader.ASSEPT_NAME, Const.RequestHeader.ASSEPT_VALUE)
                    .header(Const.RequestHeader.APP_ID_NAME, Const.RequestHeader.APP_ID_VALUE)
                    .header(Const.RequestHeader.AUTH_NAME, OAuth.getTokenValue(tokenType, token))
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        };

        HttpLoggingInterceptor logging = (new HttpLoggingInterceptor())
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(logging)
                .build();
    }
}