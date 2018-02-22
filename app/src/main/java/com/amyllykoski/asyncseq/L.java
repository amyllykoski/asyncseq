package com.amyllykoski.asyncseq;

import android.util.Log;

public class L {
  public static void deb(final String tag, final String msg) {
    if (!BuildConfig.DEBUG) return;
    Log.d(tag, msg);
  }

  public static void err(final String tag, final String msg) {
    if (!BuildConfig.DEBUG) return;
    Log.e(tag, msg);
  }
}