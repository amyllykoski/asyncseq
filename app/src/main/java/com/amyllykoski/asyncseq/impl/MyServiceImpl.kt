package com.amyllykoski.asyncseq.impl

import android.os.HandlerThread
import android.os.MessageQueue
import arrow.core.Either
import com.amyllykoski.asyncseq.api.MyService
import com.amyllykoski.asyncseq.model.Item
import com.amyllykoski.asyncseq.util.L
import com.amyllykoski.asyncseq.util.MyCallback

class MyServiceImpl(private val baseUrl: String) : MyService {
  private val handlerThread: HandlerThread

  init {
    handlerThread = HandlerThread(TAG, HandlerThread.MIN_PRIORITY)
    handlerThread.start()
    setIdleHandler()
  }

  private val TIMEOUT = 10L

  override fun getItems(): List<Item> {
    val items = MyCallback<Either<String, List<Item>>>(TIMEOUT)
    val handler = ServiceHandler(handlerThread.looper, items)
    handler.post(GetItems(baseUrl, handler))
    return items.data as List<Item>
  }

  override fun close() {
    handlerThread.quitSafely()
  }

  private fun setIdleHandler() {
    val idleHandler = MessageQueue.IdleHandler {
      L.d(TAG, "MessageQueue is idle.")
      true
    }
    handlerThread.looper.queue.addIdleHandler(idleHandler)
  }

  companion object {
    private val TAG = MyServiceImpl::class.java.simpleName
  }
}
