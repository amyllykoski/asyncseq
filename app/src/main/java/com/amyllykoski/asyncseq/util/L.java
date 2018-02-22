package com.amyllykoski.asyncseq.util;

import android.util.Log;

import com.amyllykoski.asyncseq.BuildConfig;

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