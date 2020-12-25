package com.arkhelais.groceryscraper.service;

import org.jsoup.nodes.Document;

public class NutritionTableThree implements EnergyHandler {

  private EnergyHandler next;

  @Override
  public void setNext(EnergyHandler handler) {
    next = handler;
  }

  @Override
  public Integer handle(Document nutritionTable) {
    return null;
  }

}
