package com.amyllykoski.asyncseq.impl

import android.os.HandlerThread
import arrow.core.Either
import com.amyllykoski.asyncseq.api.MyError
import com.amyllykoski.asyncseq.api.MyService
import com.amyllykoski.asyncseq.model.Item
import com.amyllykoski.asyncseq.util.MyCallback

class MyServiceImpl(private val baseUrl: String) : MyService {
  private val handlerThread: HandlerThread

  init {
    handlerThread = HandlerThread(TAG, HandlerThread.MIN_PRIORITY)
    handlerThread.start()
  }

  override fun getItems(): Either<MyError, List<Item>> {
    val items = MyCallback<List<Item>>(Companion.TIMEOUT)
    val handler = ServiceHandler(handlerThread.looper, items)
    handler.post(GetItems(baseUrl, handler))
    return items.data
  }

  override fun close() {
    handlerThread.quitSafely()
  }

  companion object {
    private val TAG = MyServiceImpl::class.java.simpleName
    private const val TIMEOUT = 10L
  }
}
