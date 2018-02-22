package com.amyllykoski.asyncseq.worker;

import android.os.Handler;

import com.amyllykoski.asyncseq.L;
import com.amyllykoski.asyncseq.api.MyService;
import com.amyllykoski.asyncseq.rest.RestClient;

import java.io.IOException;

import retrofit2.Response;

public class GetItems implements Runnable {
  private static final String TAG = GetItems.class.getSimpleName();
  private final String baseUrl;
  private final Handler handler;

  public GetItems(final String baseUrl, final Handler handler) {
    this.baseUrl = baseUrl;
    this.handler = handler;
  }

  @Override
  public void run() {
    try {
      Response response = RestClient
          .instance(baseUrl)
          .getItems()
          .execute();
      if (response.isSuccessful()) {
        L.deb(TAG, response.body().toString());
        handler.obtainMessage(MyService.MSG_OK, response.body()).sendToTarget();
      } else {
        L.err(TAG, response.errorBody().toString());
        handler.obtainMessage(MyService.MSG_NOK, response.errorBody()).sendToTarget();
      }
    } catch (IOException e) {
      L.err(TAG, e.getLocalizedMessage());
      handler.obtainMessage(MyService.MSG_NOK, e.getLocalizedMessage()).sendToTarget();
    }
  }
}
