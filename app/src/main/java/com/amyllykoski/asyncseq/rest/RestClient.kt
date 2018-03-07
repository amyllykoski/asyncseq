package com.amyllykoski.asyncseq.rest

import com.amyllykoski.asyncseq.BuildConfig

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestClient {

  private val okHttpClient: OkHttpClient
    get() {
      val logging = HttpLoggingInterceptor()
      logging.level = if (BuildConfig.DEBUG)
        HttpLoggingInterceptor.Level.BODY
      else
        HttpLoggingInterceptor.Level.NONE
      return OkHttpClient.Builder().addInterceptor(logging).build()
    }

  fun instance(hostName: String): RestService {
    val retrofit = Retrofit.Builder()
        .baseUrl(hostName)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient).build()
    return retrofit.create(RestService::class.java)
  }
}
