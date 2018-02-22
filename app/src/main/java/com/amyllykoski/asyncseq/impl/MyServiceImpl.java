package com.amyllykoski.asyncseq.impl;

import android.os.HandlerThread;
import android.os.MessageQueue;

import com.amyllykoski.asyncseq.api.MyService;
import com.amyllykoski.asyncseq.api.RestCallback;
import com.amyllykoski.asyncseq.model.Item;
import com.amyllykoski.asyncseq.util.L;

import java.util.Date;
import java.util.List;

public class MyServiceImpl implements MyService {
  private static final String TAG = MyServiceImpl.class.getSimpleName();
  private HandlerThread handlerThread;
  private String baseUrl;

  public MyServiceImpl(final String baseUrl) {
    this.baseUrl = baseUrl;
    handlerThread = new HandlerThread(TAG + "-" + new Date().getTime(), HandlerThread.MIN_PRIORITY);
    handlerThread.start();
    setIdleHandler();
  }

  @Override
  public void getItems(final RestCallback<List<Item>> items) {
    ServiceHandler<List<Item>> handler = new ServiceHandler<>(handlerThread.getLooper(), items);
    handler.post(new GetItems(baseUrl, handler));
  }

  @Override
  public void doLoop(long delayMillis, final String tag, final RestCallback<String> response) {
    ServiceHandler<String> handler = new ServiceHandler<>(handlerThread.getLooper(), response);
    handler.post(new DoLoop(delayMillis, tag, handler));
  }

  @Override
  public void close() {
    if (handlerThread != null) {
      handlerThread.quitSafely();
    }
  }

  private void setIdleHandler() {
    MessageQueue.IdleHandler idleHandler = new MessageQueue.IdleHandler() {
      @Override
      public boolean queueIdle() {
        L.d(TAG, "MessageQueue is idle.");
        return true;
      }
    };
    handlerThread.getLooper().getQueue().addIdleHandler(idleHandler);
  }
}
