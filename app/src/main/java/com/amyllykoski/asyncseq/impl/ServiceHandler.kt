package com.amyllykoski.asyncseq.impl

import android.os.Handler
import android.os.Looper
import android.os.Message
import arrow.core.Either
import com.amyllykoski.asyncseq.api.MyError.ServerError

import com.amyllykoski.asyncseq.api.RestCallbackWithEither

class ServiceHandler<T>(
    looper: Looper,
    private val restCallback: RestCallbackWithEither<T>)
  : Handler(looper) {

  @Suppress("UNCHECKED_CAST")
  override fun handleMessage(msg: Message) = when (msg.what) {
    MSG_OK ->
      restCallback.onResponse(Either.Right(msg.obj as T))
    else -> {
      restCallback.onResponse(Either.Left(ServerError()))
    }
  }

  companion object {
    const val MSG_OK = 1
    const val MSG_NOK = 2
  }
}
