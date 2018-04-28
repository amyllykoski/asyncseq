package com.amyllykoski.asyncseq.util

import arrow.core.Either
import com.amyllykoski.asyncseq.api.MyError
import com.amyllykoski.asyncseq.api.MyError.AnotherError
import com.amyllykoski.asyncseq.api.RestCallbackWithEither
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit


class MyCallback<T>(private val timeout: Long) : RestCallbackWithEither<T> {

  private var msgs: ArrayBlockingQueue<Either<MyError, T>> = ArrayBlockingQueue(INITIAL_CAPACITY)

  override fun onResponse(response: Either<MyError, T>) {
    try {
      this.msgs.put(response)
    } catch (e: InterruptedException) {
      e.printStackTrace()
    }
  }

  val data: Either<MyError, T>
    get() {
      var retVal: Either<MyError, T>? = null
      try {
        retVal = msgs.poll(timeout, TimeUnit.SECONDS)
      } catch (e: InterruptedException) {
        e.printStackTrace()
      }

      return if (retVal == null)
        Either.Left(AnotherError(Companion.UNKNOWN))
      else
        retVal
    }

  companion object {
    private const val UNKNOWN = "Unknown Error"
    private const val INITIAL_CAPACITY = 5
  }
}

