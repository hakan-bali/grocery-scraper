package com.arkhelais.groceryscraper.service;

import org.jsoup.nodes.Document;

public class NutritionTableTwo implements EnergyHandler {

  private EnergyHandler next;

  @Override
  public void setNext(EnergyHandler handler) {
    next = handler;
  }

  @Override
  public Integer handle(Document nutritionTable) {
    // TODO : Handle second type of Nutirion Table and extract KCAL value.
    if (next != null) {
      return next.handle(nutritionTable);
    }
    return null;
  }

}
