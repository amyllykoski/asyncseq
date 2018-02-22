package com.amyllykoski.asyncseq.api;

public interface RestCallback<T> {

  void onResponse(T response);

  void onFailure(String error);
}
