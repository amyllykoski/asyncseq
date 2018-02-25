package com.amyllykoski.asyncseq.impl

import android.os.Handler
import com.amyllykoski.asyncseq.impl.ServiceHandler.Companion.MSG_NOK
import com.amyllykoski.asyncseq.impl.ServiceHandler.Companion.MSG_OK

import com.amyllykoski.asyncseq.util.L
import com.amyllykoski.asyncseq.rest.RestClient

import java.io.IOException

class GetItems(private val baseUrl: String, private val handler: Handler) : Runnable {

  override fun run() {
    try {
      val response = RestClient.instance(baseUrl).items.execute()
      when {
        response.isSuccessful -> {
          L.d(TAG, response.body()!!.toString())
          handler.obtainMessage(MSG_OK, response.body()).sendToTarget()
        }
        else -> {
          L.e(TAG, response.errorBody()!!.toString())
          handler.obtainMessage(MSG_NOK, response.errorBody()).sendToTarget()
        }
      }
    } catch (e: IOException) {
      L.e(TAG, e.localizedMessage)
      handler.obtainMessage(MSG_NOK, e.localizedMessage).sendToTarget()
    }
  }

  companion object {
    private val TAG = GetItems::class.java.simpleName
  }
}
