package com.amyllykoski.asyncseq.api

import com.amyllykoski.asyncseq.model.Item

interface MyService {
  fun getItems(items: RestCallback<List<Item>>)
  fun close()
}
