package com.arkhelais.groceryscraper.service;

import static com.arkhelais.groceryscraper.util.Constants.KCAL_OPTION1;

import java.util.Optional;
import org.jsoup.select.Elements;

public class NutritionTableOne implements EnergyHandler {

  private final EnergyHandler next;

  public NutritionTableOne(EnergyHandler energyHandler) {
    next = energyHandler;
  }

  @Override
  public Integer handle(Elements elements) {
    return Optional.ofNullable(getKcal(elements, KCAL_OPTION1)).orElse(callNext(next, elements));
  }

}