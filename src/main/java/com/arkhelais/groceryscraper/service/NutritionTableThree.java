package com.arkhelais.groceryscraper.service;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class NutritionTableThree implements EnergyHandler {

  private EnergyHandler next;

  @Override
  public void setNext(EnergyHandler handler) {
    next = handler;
  }

  @Override
  public Integer handle(Document nutritionTable) {
    Elements elements = nutritionTable.getElementsByTag("td");
    if (!elements.isEmpty() && elements.size() > 3) {
      return Integer.parseInt(elements.get(3).text());
    }
    if (next != null) {
      return next.handle(nutritionTable);
    }
    return null;
  }

}
