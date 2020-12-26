package com.arkhelais.groceryscraper.service;

import static com.arkhelais.groceryscraper.util.Constants.KCAL_NULL;
import static com.arkhelais.groceryscraper.util.Constants.KCAL_LABEL;

import org.jsoup.select.Elements;

public interface EnergyHandler {

  default Integer callNext(EnergyHandler next, Elements elements) {
    return next == null ? KCAL_NULL : next.handle(elements);
  }

  default Integer getKcal(Elements elements, int index) {
    if (elements.size() > index) {
      String energyText = elements.get(index).text();
      if (energyText.contains(KCAL_LABEL))
        return Integer.parseInt(energyText.substring(0, energyText.indexOf(KCAL_LABEL)));
    }
    return KCAL_NULL;
  }

  Integer handle(Elements elements);

}
