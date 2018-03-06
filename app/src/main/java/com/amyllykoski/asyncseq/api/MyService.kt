package com.amyllykoski.asyncseq.api

import arrow.core.Either
import com.amyllykoski.asyncseq.model.Item

interface MyService {
  fun getItems(): Either<MyError, List<Item>>
  fun close()
}

sealed class MyError {
  class AnotherError : MyError()
  class ServerError : MyError()
}
