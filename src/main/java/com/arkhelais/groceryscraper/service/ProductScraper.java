package com.arkhelais.groceryscraper.service;

import static com.arkhelais.groceryscraper.util.Constants.CSS_PRODUCT;
import static com.arkhelais.groceryscraper.util.Constants.DEFAULT_URL;
import static com.arkhelais.groceryscraper.util.Constants.VAT;

import com.arkhelais.groceryscraper.dto.Output;
import com.arkhelais.groceryscraper.dto.Product;
import com.arkhelais.groceryscraper.dto.Total;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ProductScraper {
  private final Output output;
  private final EnergyHandler energyHandler;
  private String categoryPageUrl = DEFAULT_URL;

  public ProductScraper() {
    output = Output.builder().results(new ArrayList<>()).build();
    energyHandler = new NutritionTableOne();
    EnergyHandler energyHandlerTwo = new NutritionTableTwo();
    energyHandler.setNext(energyHandlerTwo);
    EnergyHandler energyHandlerThree = new NutritionTableThree();
    energyHandlerTwo.setNext(energyHandlerThree);
  }

  public void setCategoryPageUrl(String categoryPageUrl) {
    this.categoryPageUrl = categoryPageUrl;
  }

  public String extractProductsAsJson() {
    String result = "No product found";
    try {
      Elements products = Jsoup.connect(categoryPageUrl).get().getElementsByClass(CSS_PRODUCT);
      for (Element product : products) {
        output.getResults().add(getProductInfo(product));
      }
      output.setTotal(generateTotal());
      result = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(output);
    } catch (JsonProcessingException e) {
      System.err.println("\nJsonProcessingException occurred\n");
    } catch (IOException e) {
      System.err.println("\nIOException occurred\n");
    }
    return result;
  }

  private Double getGross() {
    return output.getResults().stream()
        .map(Product::getUnitPrice)
        .reduce(0.0, Double::sum);
  }

  private Double getVat(Double gross) {
    Double vat = (gross * 100) / (100 + VAT);
    return Double.parseDouble(String.format("%.2f", vat));
  }

  private Total generateTotal() {
    Double gross = getGross();
    return Total.builder()
        .gross(gross)
        .vat(getVat(gross))
        .build();
  }

  private Double getUnitPrice(Document product) {
    try {
      String priceRawText = product.getElementsByClass("pricePerUnit").text();
      return Double.parseDouble(priceRawText.substring(1, priceRawText.indexOf("/")));
    } catch (Exception e) {
      return 0.0;
    }
  }

  private String getDescription(Document product) {
    try {
      return product.getElementsByClass("productText").get(0).text();
    } catch (Exception e) {
      return "";
    }
  }

  private Product getProductInfo(Element product) throws IOException {
    Document productDocument =
        Jsoup.connect(product.getElementsByTag("a").first().attr("abs:href")).get();
    return Product.builder()
        .title(product.text())
        .unitPrice(getUnitPrice(productDocument))
        .description(getDescription(productDocument))
        .kcal(energyHandler.handle(productDocument))
        .build();
  }

}