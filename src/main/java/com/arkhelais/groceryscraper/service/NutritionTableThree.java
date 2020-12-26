package com.arkhelais.groceryscraper.service;

import org.jsoup.select.Elements;

public class NutritionTableThree implements EnergyHandler {

  private final EnergyHandler next;

  public NutritionTableThree(EnergyHandler handler) {
    next = handler;
  }

  @Override
  public Integer handle(Elements elements) {
    return callNext(next, elements);
  }

}