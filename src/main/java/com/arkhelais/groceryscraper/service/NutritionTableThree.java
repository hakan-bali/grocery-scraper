package com.arkhelais.groceryscraper.service;

import static com.arkhelais.groceryscraper.util.Constants.KCAL_INDEX_3;

import org.jsoup.select.Elements;

public class NutritionTableThree extends EnergyHandler {

  private final EnergyHandler next;

  public NutritionTableThree(EnergyHandler handler) {
    next = handler;
  }

  @Override
  public Integer handle(Elements elements) {
    Integer result = null;
    Integer kcalWithoutLabel = getKcalWithoutLabel(elements, KCAL_INDEX_3);
    if (kcalWithoutLabel != null)
      result = kcalWithoutLabel;
    if (result == null)
      result = callNext(next, elements);
    return result;
  }

}