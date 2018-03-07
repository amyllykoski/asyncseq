package com.amyllykoski.asyncseq.api

import arrow.core.Either

// Original RestCallback.
@Suppress("UNUSED")
interface RestCallback<in T> {
  fun onResponse(response: T)
  fun onFailure(error: String)
}

interface RestCallbackWithEither<in T> {
  fun onResponse(response: Either<MyError, T>)
}
