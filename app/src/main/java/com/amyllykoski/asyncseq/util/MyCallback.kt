package com.amyllykoski.asyncseq.util

import com.amyllykoski.asyncseq.api.RestCallback
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit

class MyCallback<in T> : RestCallback<T> {

  private var msg = ArrayBlockingQueue<T>(5)
  private var err = ArrayBlockingQueue<String>(5)

  override fun onResponse(response: T) {
    try {
      this.msg.put(response)
    } catch (e: InterruptedException) {
      e.printStackTrace()
    }
  }

  override fun onFailure(error: String) {
    try {
      err.put(error)
    } catch (e: InterruptedException) {
      e.printStackTrace()
    }
  }

  val data: Any
    get() {
      var retVal: Any? = null
      try {
        retVal = msg.poll(10, TimeUnit.SECONDS)
      } catch (e: InterruptedException) {
        e.printStackTrace()
      }
      return if (retVal == null) Any() else retVal
    }


  companion object {
    private val TAG = MyCallback::class.java!!.getSimpleName()
  }
}

