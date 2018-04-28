package com.amyllykoski.asyncseq.util

import android.util.Log

import com.amyllykoski.asyncseq.BuildConfig

object L {
  fun d(tag: String, msg: String) {
    if (!BuildConfig.DEBUG) return
    Log.d(tag, msg)
  }

  fun e(tag: String, msg: String) {
    if (!BuildConfig.DEBUG) return
    Log.e(tag, msg)
  }
}
