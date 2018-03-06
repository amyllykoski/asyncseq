package com.amyllykoski.asyncseq.impl

import android.os.Handler
import com.amyllykoski.asyncseq.impl.ServiceHandler.Companion.MSG_NOK
import com.amyllykoski.asyncseq.impl.ServiceHandler.Companion.MSG_OK
import com.amyllykoski.asyncseq.rest.RestClient
import java.io.IOException

class GetItems(private val baseUrl: String, private val handler: Handler) : Runnable {

  override fun run() {
    try {
      val response = RestClient.instance(baseUrl).items.execute()
      when {
        response.isSuccessful -> {
          handler.obtainMessage(MSG_OK, response.body()).sendToTarget()
        }
        else -> {
          handler.obtainMessage(MSG_NOK, response.errorBody()).sendToTarget()
        }
      }
    } catch (e: IOException) {
      handler.obtainMessage(MSG_NOK, e.localizedMessage).sendToTarget()
    }
  }
}
