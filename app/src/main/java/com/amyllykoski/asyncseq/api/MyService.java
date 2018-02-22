package com.amyllykoski.asyncseq.api;

import com.amyllykoski.asyncseq.model.Item;
import com.amyllykoski.asyncseq.model.RestCallback;

import java.util.List;

public interface MyService {
  void getItems(RestCallback<List<Item>> items);
}
