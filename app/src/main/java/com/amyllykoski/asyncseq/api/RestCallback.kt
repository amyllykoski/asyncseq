package com.amyllykoski.asyncseq.api

import arrow.core.Either

interface RestCallback<in T> {
  fun onResponse(response: T)
  fun onFailure(error: String)
}

interface RestCallbackWithEither<in T> {
  fun onResponse(response: Either<String, T>)
}
