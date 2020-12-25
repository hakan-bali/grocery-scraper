package com.arkhelais.groceryscraper;

import com.arkhelais.groceryscraper.service.ProductScraper;

public class Main {

  public static void main(String[] args) {
    System.out.println(new ProductScraper().extractProductsAsJson());
  }

}