package com.amyllykoski.asyncseq.impl

import android.os.Handler
import android.os.Looper
import android.os.Message

import com.amyllykoski.asyncseq.api.RestCallback

class ServiceHandler<T>(
    looper: Looper,
    private val restCallback: RestCallback<T>) : Handler(looper) {

  @Suppress("UNCHECKED_CAST")
  override fun handleMessage(msg: Message) = when (msg.what) {
    MSG_OK ->
      restCallback.onResponse(msg.obj as T)
    else -> {
      restCallback.onFailure(msg.obj.toString())
    }
  }

  companion object {
    const val MSG_OK = 1
    const val MSG_NOK = 2
  }
}
