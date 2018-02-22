package com.amyllykoski.asyncseq.api;

import com.amyllykoski.asyncseq.model.Item;
import com.amyllykoski.asyncseq.model.RestCallback;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface MyService {
  void getItems(RestCallback<List<Item>> items);

  void callMock(long delayMillis, String tag, RestCallback<String> response);
}
