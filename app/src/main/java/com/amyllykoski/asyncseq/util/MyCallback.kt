package com.amyllykoski.asyncseq.util

import arrow.core.Either
import com.amyllykoski.asyncseq.api.RestCallbackWithEither
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit

class MyCallback<T>(private val timeout: Long) : RestCallbackWithEither<T> {

  private var msgs: ArrayBlockingQueue<Either<String, T>> = ArrayBlockingQueue(5)

  override fun onResponse(response: Either<String, T>) {
    try {
      this.msgs.put(response)
    } catch (e: InterruptedException) {
      e.printStackTrace()
    }
  }

  val data: Either<String, T>
    get() {
      var retVal: Either<String, T>? = null
      try {
        retVal = msgs.poll(timeout, TimeUnit.SECONDS)
      } catch (e: InterruptedException) {
        e.printStackTrace()
      }
      return if (retVal == null) Either.Left("Unknown error") else retVal
    }


  companion object {
    private val TAG = MyCallback::class.java!!.simpleName
  }
}

