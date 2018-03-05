package com.amyllykoski.asyncseq;

import android.support.test.espresso.core.internal.deps.guava.base.Predicate;

import com.amyllykoski.asyncseq.api.MyService;
import com.amyllykoski.asyncseq.api.RestCallback;
import com.amyllykoski.asyncseq.impl.MyServiceImpl;
import com.amyllykoski.asyncseq.model.Item;
import com.amyllykoski.asyncseq.util.L;
import com.amyllykoski.asyncseq.util.RandomString;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class MyServiceImplTest {
  private static final String TAG = MyServiceImplTest.class.getSimpleName();
  private MockWebServer server;

  @Before
  public void setUp() throws Exception {
    server = new MockWebServer();
    server.start();
  }

  @Test
  public void getItemsFromMockServer() throws Exception {
    final String json = new Gson().toJson(listOfItems());
    server.enqueue(new MockResponse().setBody(json));

    MyService sut = new MyServiceImpl(server.url("/").toString());
    sut.getItems(new RestCallback<List<Item>>() {
      @Override
      public void onResponse(List<Item> response) {
        L.INSTANCE.d(TAG, String.format("Received: %s", response.toString()));
        for (Item i : response) {
          assertTrue(json.contains(i.getDescription()));
          assertTrue(json.contains(i.getId()));
        }
      }

      @Override
      public void onFailure(String error) {
        L.INSTANCE.e(TAG, error);
        fail();
      }
    });
  }

  private class Callback<T> implements RestCallback<T> {
    private Predicate<T> predicate;

    Callback(Predicate<T> predicate) {
      this.predicate = predicate;
    }

    @Override
    public void onResponse(T response) {
      L.INSTANCE.d(TAG, String.format("Received: %s", response));
      assertTrue(String.format("%s matches with %s", predicate.toString(), response),
          predicate.apply(response));
    }

    @Override
    public void onFailure(String error) {
      L.INSTANCE.e(TAG, String.format("Error: %s", error));
    }
  }

  private List<Item> listOfItems() {
    List<Item> generated = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      generated.add(new Item(String.format("%d", System.nanoTime() + i), rndStr(10)));
    }
    return generated;
  }

  private String rndStr(int length) {
    return new RandomString(length, ThreadLocalRandom.current()).nextString();
  }
}