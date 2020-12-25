package com.arkhelais.groceryscraper.service;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class NutritionTableOne implements EnergyHandler {

  private EnergyHandler next;

  @Override
  public void setNext(EnergyHandler handler) {
    next = handler;
  }

  @Override
  public Integer handle(Document nutritionTable) {
    Elements elements = nutritionTable.getElementsByTag("td");
    if (!elements.isEmpty() && elements.size() > 2) {
      String energyText = elements.get(2).text();
      int kcalIndex = energyText.indexOf("kcal");
      if (kcalIndex > 0) {
        return Integer.parseInt(energyText.substring(0, kcalIndex));
      }
    }
    if (next != null) {
      return next.handle(nutritionTable);
    }
    return null;
  }

}