package com.amyllykoski.asyncseq;

import com.amyllykoski.asyncseq.api.MyService;
import com.amyllykoski.asyncseq.model.Item;
import com.amyllykoski.asyncseq.model.RestCallback;
import com.amyllykoski.asyncseq.util.L;
import com.amyllykoski.asyncseq.util.RandomString;
import com.google.gson.Gson;

import org.junit.After;
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

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void getItemsFromMockServer() throws Exception {
    List<Item> resp = listOf(10);
    final String json = new Gson().toJson(resp);
    server.enqueue(new MockResponse().setBody(json));

    MyService sut = new MyServiceImpl(server.url("/").toString());
    sut.getItems(new RestCallback<List<Item>>() {
      @Override
      public void onResponse(List<Item> response) {
        L.deb(TAG, String.format("Received: %s", response.toString()));
        for (Item i : response) {
          assertTrue(json.contains(i.getDescription()));
          assertTrue(json.contains(i.getId()));
        }
      }

      @Override
      public void onFailure(String error) {
        L.err(TAG, error);
        fail();
      }
    });
  }

  @Test
  public void getItemsFromThread() throws Exception {
    List<Item> resp = listOf(10);
    final String json = new Gson().toJson(resp);
    server.enqueue(new MockResponse().setBody(json));

    MyService sut = new MyServiceImpl(server.url("/").toString());
    sut.getItems(new RestCallback<List<Item>>() {
      @Override
      public void onResponse(List<Item> response) {
        L.deb(TAG, String.format("Received: %s", response.toString()));
        for (Item i : response) {
          assertTrue(json.contains(i.getDescription()));
          assertTrue(json.contains(i.getId()));
        }
      }

      @Override
      public void onFailure(String error) {
        L.err(TAG, error);
        fail();
      }
    });
  }

  private List<Item> listOf(int count) {
    List<Item> generated = new ArrayList();
    for (int i = 0; i < count; i++) {
      generated.add(new Item(System.nanoTime() + i + "",
          new RandomString(10, ThreadLocalRandom.current()).nextString()));
    }
    return generated;
  }
}