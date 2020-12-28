package com.arkhelais.groceryscraper.service;

import org.jsoup.select.Elements;

public class NutritionTableThree extends EnergyHandler {

  private final EnergyHandler next;

  public NutritionTableThree(EnergyHandler handler) {
    next = handler;
  }

  @Override
  public Integer handle(Elements elements) {
    Integer result = null;
    Integer kcalWithoutLabel = getKcalWithoutLabel(elements);
    if (kcalWithoutLabel != null)
      result = kcalWithoutLabel;
    if (result == null)
      result = callNext(next, elements);
    return result;
  }

}