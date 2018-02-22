package com.amyllykoski.asyncseq.model;

public class Item {
  private String id;
  private String description;

  public Item(final String id, final String description) {
    this.id = id;
    this.description = description;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return "Item{" +
        "id='" + id + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}
