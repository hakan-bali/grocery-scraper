package com.arkhelais.groceryscraper.service;

import static com.arkhelais.groceryscraper.util.Constants.KCAL_INDEX_3;
import static com.arkhelais.groceryscraper.util.Constants.KCAL_NULL;
import static com.arkhelais.groceryscraper.util.Constants.KCAL_LABEL;

import org.jsoup.select.Elements;

public abstract class EnergyHandler {

  Integer callNext(EnergyHandler next, Elements elements) {
    return next == null ? KCAL_NULL : next.handle(elements);
  }

  Integer getKcalWithLabel(Elements elements, int index) {
    if (elements.size() > index) {
      String energyText = elements.get(index).text();
      if (energyText.contains(KCAL_LABEL))
        return Integer.parseInt(energyText.substring(0, energyText.indexOf(KCAL_LABEL)));
    }
    return KCAL_NULL;
  }

  Integer getKcalWithoutLabel(Elements elements) {
    if (elements.isEmpty())
      return KCAL_NULL;
    try {
      return Integer.parseInt(elements.get(KCAL_INDEX_3).text());
    } catch (Exception e) {
      return KCAL_NULL;
    }
  }

  abstract Integer handle(Elements elements);

}
