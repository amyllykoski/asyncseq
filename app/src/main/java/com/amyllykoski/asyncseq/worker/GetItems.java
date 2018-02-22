package com.amyllykoski.asyncseq.worker;

import android.os.Handler;

import com.amyllykoski.asyncseq.util.L;
import com.amyllykoski.asyncseq.rest.RestClient;

import java.io.IOException;

import retrofit2.Response;

import static com.amyllykoski.asyncseq.worker.ServiceHandler.MSG_NOK;
import static com.amyllykoski.asyncseq.worker.ServiceHandler.MSG_OK;

public class GetItems implements Runnable {
  private static final String TAG = GetItems.class.getSimpleName();
  private final Handler handler;
  private final String baseUrl;

  public GetItems(final String baseUrl, final Handler handler) {
    this.baseUrl = baseUrl;
    this.handler = handler;
  }

  @Override
  public void run() {
    try {
      Response response = RestClient.instance(baseUrl).getItems().execute();
      if (response.isSuccessful()) {
        L.deb(TAG, response.body().toString());
        handler.obtainMessage(MSG_OK, response.body()).sendToTarget();
      } else {
        L.err(TAG, response.errorBody().toString());
        handler.obtainMessage(MSG_NOK, response.errorBody()).sendToTarget();
      }
    } catch (IOException e) {
      L.err(TAG, e.getLocalizedMessage());
      handler.obtainMessage(MSG_NOK, e.getLocalizedMessage()).sendToTarget();
    }
  }
}
