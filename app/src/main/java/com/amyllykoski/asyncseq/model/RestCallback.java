package com.amyllykoski.asyncseq.model;

public interface RestCallback<T> {

  void onResponse(T response);

  void onFailure(String error);
}
