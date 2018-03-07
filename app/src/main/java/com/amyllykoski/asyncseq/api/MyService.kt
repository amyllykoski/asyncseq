package com.amyllykoski.asyncseq.api

import arrow.core.Either
import com.amyllykoski.asyncseq.model.Item

interface MyService {
  fun getItems(): Either<MyError, List<Item>>
  fun close()
}

sealed class MyError {
  object ServerError : MyError()
  data class AnotherError(val msg: String) : MyError()
}
