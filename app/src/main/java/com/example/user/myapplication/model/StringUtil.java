package com.example.user.myapplication.model;

import static com.example.user.myapplication.model.Const.EMPTY_STRING;

public class StringUtil {
    public static String nonNull(String str) {
        return str != null ? str : EMPTY_STRING;
    }
}
