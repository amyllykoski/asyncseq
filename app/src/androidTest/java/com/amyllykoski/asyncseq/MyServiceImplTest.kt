package com.amyllykoski.asyncseq

import arrow.core.Either
import com.amyllykoski.asyncseq.api.MyError
import com.amyllykoski.asyncseq.impl.MyServiceImpl
import com.amyllykoski.asyncseq.model.Item
import com.amyllykoski.asyncseq.util.RandomString
import com.google.gson.Gson
import junit.framework.Assert.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class MyServiceImplTest {

  private lateinit var server: MockWebServer

  @Before
  @Throws(Exception::class)
  fun setUp() {
    server = MockWebServer()
    server.start()
  }

  @Test
  @Throws(Exception::class)
  fun getItemsFromMockServer() {
    val json = Gson().toJson(listOfItems())
    server.enqueue(MockResponse().setResponseCode(200).setBody(json))

    val sut = MyServiceImpl(server.url("/").toString())
    val response = sut.getItems()
    response.map {
      // List<Item>
      it.map {
        // Item
        assertTrue(json.contains(it.description))
        assertTrue(json.contains(it.id))
      }
    }
  }

  @Test
  @Throws(Exception::class)
  fun getItemsWithError() {
    server.enqueue(MockResponse().setResponseCode(500))
    val sut = MyServiceImpl(server.url("/").toString())

    // Should be a Left value.
    val response = sut.getItems()
    assertTrue(response.isLeft())

    // The error type should be ServerError.
    fun shouldBeServerError(response: Either<MyError, List<Item>>) {
      when (response) {
        is Either.Left -> when (response.a) {
          !is MyError.ServerError -> fail()
        }
        else -> fail()
      }
    }

    // Left value is not mapped.
    fun shouldNotMap() {
      response.map {
        it.map {
          fail()
        }
      }
    }

    shouldBeServerError(response)
    shouldNotMap()
  }

  private fun listOfItems(): List<Item> {
    val generated = ArrayList<Item>()
    for (i in 0 until NUM_OF_ITEMS) {
      generated.add(Item(String.format("%d", System.nanoTime() + i), rndStr(10)))
    }
    return generated
  }

  private fun rndStr(length: Int): String {
    return RandomString(length, ThreadLocalRandom.current()).nextString()
  }

  companion object {
    private const val NUM_OF_ITEMS = 10
  }
}