package com.arkhelais.groceryscraper.service;

import static com.arkhelais.groceryscraper.util.Constants.KCAL_INDEX_2;

import org.jsoup.select.Elements;

public class NutritionTableOne extends EnergyHandler {

  private final EnergyHandler next;

  public NutritionTableOne(EnergyHandler energyHandler) {
    next = energyHandler;
  }

  @Override
  public Integer handle(Elements elements) {
    Integer result = null;
    Integer kcalWithLabel = getKcalWithLabel(elements, KCAL_INDEX_2);
    if (kcalWithLabel != null)
      result = kcalWithLabel;
    if (result == null)
      result = callNext(next, elements);
    return result;
  }

}