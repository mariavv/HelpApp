package com.example.user.myapplication.model.remote;

import com.example.user.myapplication.model.entity.Token;

import static com.example.user.myapplication.model.Const.EMPTY_STRING;

public class OAuth {
    static final String DEF_VALUE = EMPTY_STRING;

    private static final String TYPE_SEPARATE = " ";

    private static String typeToken = DEF_VALUE;
    private static String token = DEF_VALUE;

    public static boolean setToken(Token token) {
        String tokenType = token.getTokenType();
        String accessToken = token.getAccessToken();

        if (tokenType == null || accessToken == null) {
            return false;
        }

        setToken(token.getTokenType(), token.getAccessToken());
        return true;
    }

    public static void setToken(String tokenType, String accessToken) {
        OAuth.typeToken = tokenType;
        OAuth.token = accessToken;
    }

    static String getTypeToken() {
        return typeToken != null ? typeToken : DEF_VALUE;
    }

    static String getToken() {
        return token != null ? token : DEF_VALUE;
    }

    static String getTokenValue(String tokenType, String token) {
        return tokenType + TYPE_SEPARATE + token;
    }
}