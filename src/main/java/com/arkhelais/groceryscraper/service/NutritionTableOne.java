package com.arkhelais.groceryscraper.service;

import org.jsoup.nodes.Document;

public class NutritionTableOne implements EnergyHandler {

  private EnergyHandler next;

  @Override
  public void setNext(EnergyHandler handler) {
    next = handler;
  }

  @Override
  public Integer handle(Document nutritionTable) {
    String energyText = "";
    Integer kcal = null;
    int kcalIndex;

    if (!nutritionTable.getElementsByTag("td").isEmpty()) {
      energyText = nutritionTable.getElementsByTag("td").get(2).text();
      kcalIndex = energyText.indexOf("kcal");
      if (kcalIndex > 0) {
        return Integer.parseInt(energyText.substring(0, kcalIndex));
      }
    }

    if (next != null) {
      return next.handle(nutritionTable);
    }

    return kcal;
  }

}