package vn.poly.jeanshop.src.model;

import android.graphics.Point;

public  class StoreKey {
    private static String token;
    private static Point size;

    public static Point getSize() {
        return size;
    }

    public static void setSize(Point size) {
        StoreKey.size = size;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String tokenn) {
        token = tokenn;
    }
}
