package com.arkhelais.groceryscraper.service;

import org.jsoup.nodes.Document;

public interface EnergyHandler {

  void setNext(EnergyHandler handler);
  Integer handle(Document nutritionTable);

}
