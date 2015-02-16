package com.vbevans94.lastfm.utils;

import android.view.View;

public class UiUtils {

    public static void show(View view) {
        view.setVisibility(View.VISIBLE);
    }

    public static void hide(View view) {
        view.setVisibility(View.GONE);
    }
}
