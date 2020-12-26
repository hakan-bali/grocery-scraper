package com.arkhelais.groceryscraper.service;

import static com.arkhelais.groceryscraper.util.Constants.KCAL_INDEX_2;

import java.util.Optional;
import org.jsoup.select.Elements;

public class NutritionTableOne implements EnergyHandler {

  private final EnergyHandler next;

  public NutritionTableOne(EnergyHandler energyHandler) {
    next = energyHandler;
  }

  @Override
  public Integer handle(Elements elements) {
    return Optional.ofNullable(getKcalWithLabel(elements, KCAL_INDEX_2))
        .orElseGet(() -> callNext(next, elements));
  }

}