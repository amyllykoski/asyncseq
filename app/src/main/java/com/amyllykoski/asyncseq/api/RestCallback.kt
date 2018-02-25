package com.amyllykoski.asyncseq.api

interface RestCallback<in T> {
  fun onResponse(response: T)
  fun onFailure(error: String)
}
