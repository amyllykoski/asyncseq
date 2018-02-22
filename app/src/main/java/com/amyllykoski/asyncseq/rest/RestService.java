package com.amyllykoski.asyncseq.rest;

import com.amyllykoski.asyncseq.model.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestService {
  @GET("items")
  Call<List<Item>> getItems();
}
