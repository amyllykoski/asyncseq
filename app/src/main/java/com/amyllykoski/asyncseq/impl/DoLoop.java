package com.amyllykoski.asyncseq.impl;

import android.os.Handler;

import com.amyllykoski.asyncseq.util.L;

import static com.amyllykoski.asyncseq.impl.ServiceHandler.MSG_NOK;
import static com.amyllykoski.asyncseq.impl.ServiceHandler.MSG_OK;

public class DoLoop implements Runnable {
  private static final String TAG = DoLoop.class.getSimpleName();
  private final long delayMillis;
  private final Handler handler;
  private final String tag;

  public DoLoop(long delayMillis, String tag, final Handler handler) {
    this.delayMillis = delayMillis;
    this.tag = tag;
    this.handler = handler;
  }

  @Override
  public void run() {
    try {
      Thread.sleep(delayMillis);
      L.d(TAG, String.format("Sending: %s", tag));
      handler.obtainMessage(MSG_OK, tag).sendToTarget();
    } catch (InterruptedException e) {
      handler.obtainMessage(MSG_NOK, e.getLocalizedMessage()).sendToTarget();
    }
  }
}
