package com.arkhelais.groceryscraper.service;

import static com.arkhelais.groceryscraper.util.Constants.KCAL_INDEX_3;

import java.util.Optional;
import org.jsoup.select.Elements;

public class NutritionTableThree implements EnergyHandler {

  private final EnergyHandler next;

  public NutritionTableThree(EnergyHandler handler) {
    next = handler;
  }

  @Override
  public Integer handle(Elements elements) {
    return Optional.ofNullable(getKcalWithoutLabel(elements, KCAL_INDEX_3))
        .orElseGet( () -> callNext(next, elements));
  }

}