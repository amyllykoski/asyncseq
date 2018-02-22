package com.amyllykoski.asyncseq.rest;

import com.amyllykoski.asyncseq.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

  public static RestService instance(final String hostName) {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(hostName)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getOkHttpClient()).build();
    return retrofit.create(RestService.class);
  }

  private static OkHttpClient getOkHttpClient() {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(BuildConfig.DEBUG ?
        HttpLoggingInterceptor.Level.BODY :
        HttpLoggingInterceptor.Level.NONE);
    return new OkHttpClient.Builder().addInterceptor(logging).build();
  }
}
