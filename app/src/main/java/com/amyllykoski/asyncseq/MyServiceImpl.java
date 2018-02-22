package com.amyllykoski.asyncseq;

import android.os.HandlerThread;

import com.amyllykoski.asyncseq.api.MyService;
import com.amyllykoski.asyncseq.model.Item;
import com.amyllykoski.asyncseq.model.RestCallback;
import com.amyllykoski.asyncseq.worker.GetItems;
import com.amyllykoski.asyncseq.worker.MyHandler;

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
  }

  @Override
  public void getItems(RestCallback<List<Item>> items) {
    MyHandler<List<Item>> handler = new MyHandler(handlerThread.getLooper(), items);
    handler.post(new GetItems(baseUrl, handler));
  }
}
