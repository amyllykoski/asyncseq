package com.amyllykoski.asyncseq.worker;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.amyllykoski.asyncseq.model.RestCallback;

import static com.amyllykoski.asyncseq.api.MyService.MSG_OK;

public class MyHandler<T> extends Handler {

  private RestCallback<T> restCallback;

  public MyHandler(Looper looper, RestCallback<T> restCallback) {
    super(looper);
    this.restCallback = restCallback;
  }

  @Override
  public void handleMessage(Message msg) {
    switch (msg.what) {
      case MSG_OK:
        restCallback.onResponse((T) msg.obj);
        break;
      default:
        String error = msg.obj != null ? msg.obj.toString() : "?";
        restCallback.onFailure(error);
        break;
    }
  }
}
