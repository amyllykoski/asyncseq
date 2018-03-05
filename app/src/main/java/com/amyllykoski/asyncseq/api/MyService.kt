package com.amyllykoski.asyncseq.api

import com.amyllykoski.asyncseq.model.Item

interface MyService {
  fun getItems(): List<Item>
  fun close()
}
