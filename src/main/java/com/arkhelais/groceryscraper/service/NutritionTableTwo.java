package com.arkhelais.groceryscraper.service;

import static com.arkhelais.groceryscraper.util.Constants.KCAL_OPTION2;

import java.util.Optional;
import org.jsoup.select.Elements;

public class NutritionTableTwo implements EnergyHandler {

  private final EnergyHandler next;

  public NutritionTableTwo(EnergyHandler energyHandler) {
    next = energyHandler;
  }

  @Override
  public Integer handle(Elements elements) {
    return Optional.ofNullable(getKcal(elements, KCAL_OPTION2)).orElse(callNext(next, elements));
  }

}