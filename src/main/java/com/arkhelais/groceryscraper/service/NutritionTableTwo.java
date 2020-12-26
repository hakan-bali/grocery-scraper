package com.arkhelais.groceryscraper.service;

import static com.arkhelais.groceryscraper.util.Constants.KCAL_INDEX_3;

import java.util.Optional;
import org.jsoup.select.Elements;

public class NutritionTableTwo implements EnergyHandler {

  private final EnergyHandler next;

  public NutritionTableTwo(EnergyHandler energyHandler) {
    next = energyHandler;
  }

  @Override
  public Integer handle(Elements elements) {
    return Optional.ofNullable(getKcalWithLabel(elements, KCAL_INDEX_3))
        .orElseGet(() -> callNext(next, elements));
  }

}