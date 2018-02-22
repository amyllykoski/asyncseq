package com.amyllykoski.asyncseq;

import com.amyllykoski.asyncseq.api.MyService;
import com.amyllykoski.asyncseq.model.Item;
import com.amyllykoski.asyncseq.model.RestCallback;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.*;

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
  public void getItems() throws Exception {
    MyService sut = new MyServiceImpl(server.url("/").toString());
    List<Item> resp = new ArrayList();
    resp.add(new Item("123", "Da Description."));
    server.enqueue(new MockResponse().setBody(new Gson().toJson(resp)));
    sut.getItems(new RestCallback<List<Item>>() {
      @Override
      public void onResponse(List<Item> response) {
        L.deb(TAG, response.toString());
      }

      @Override
      public void onFailure(String error) {
        L.err(TAG, error);
      }
    });
  }
}