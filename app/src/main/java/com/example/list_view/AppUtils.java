package com.example.list_view;

public class AppUtils {

    public static String QT(String data) {
        if (data != null)
            return "'" + data + "'";
        return "''";
    }
}
