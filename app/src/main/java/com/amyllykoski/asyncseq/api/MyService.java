package com.amyllykoski.asyncseq.api;

import com.amyllykoski.asyncseq.model.Item;

import java.util.List;

public interface MyService {
  void getItems(RestCallback<List<Item>> items);

  void doLoop(long delayMillis, String tag, RestCallback<String> response);

  void close();
}
