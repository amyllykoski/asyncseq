package com.amyllykoski.asyncseq.impl;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.amyllykoski.asyncseq.api.RestCallback;

public class ServiceHandler<T> extends Handler {

  static final int MSG_OK = 1;
  static final int MSG_NOK = 2;

  private RestCallback<T> restCallback;

  public ServiceHandler(Looper looper, RestCallback<T> restCallback) {
    super(looper);
    if (restCallback == null) {
      throw new IllegalArgumentException("RestCallback cannot be null.");
    }
    this.restCallback = restCallback;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void handleMessage(Message msg) {
    switch (msg.what) {
      case MSG_OK:
      if(msg.obj != null) restCallback.onResponse((T) msg.obj);
        break;
      default:
        String error = msg.obj != null ? msg.obj.toString() : "?";
        restCallback.onFailure(error);
        break;
    }
  }
}
