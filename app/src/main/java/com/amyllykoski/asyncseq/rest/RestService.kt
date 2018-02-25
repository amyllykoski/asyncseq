package com.amyllykoski.asyncseq.rest

import com.amyllykoski.asyncseq.model.Item

import retrofit2.Call
import retrofit2.http.GET

interface RestService {
  @get:GET("items")
  val items: Call<List<Item>>
}
