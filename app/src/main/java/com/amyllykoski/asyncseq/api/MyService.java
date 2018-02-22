package com.amyllykoski.asyncseq.api;

import com.amyllykoski.asyncseq.model.Item;
import com.amyllykoski.asyncseq.model.RestCallback;

import java.util.List;

public interface MyService {
  int MSG_OK = 1;
  int MSG_NOK = 2;

  void getItems(RestCallback<List<Item>> items);
}
