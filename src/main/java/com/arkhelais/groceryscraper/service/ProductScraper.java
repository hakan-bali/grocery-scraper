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
  private final Output output = Output.builder().results(new ArrayList<>()).build();
  private EnergyHandler energyHandler;
  private String categoryPageUrl = DEFAULT_URL;

  public ProductScraper() {
    EnergyHandler energyHandler = new NutritionTableOne();
    EnergyHandler energyHandlerTwo = new NutritionTableTwo();
    EnergyHandler energyHandlerThree = new NutritionTableThree();
    energyHandler.setNext(energyHandlerTwo);
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
        .map(Product::getUnit_price)
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

  private Product getProductInfo(Element product) throws IOException {
    Document docProd;
    String priceRawText;
    String priceText;
    String productDescription;
    Double price;

    docProd = Jsoup.connect(product.getElementsByTag("a").first().attr("abs:href")).get();
    priceRawText = docProd.getElementsByClass("pricePerUnit").text();
    priceText = priceRawText.substring(1, priceRawText.indexOf("/"));
    productDescription = docProd.getElementsByClass("productText").get(0).text();

    price = Double.parseDouble(priceText);

    return Product.builder()
        .title(product.text())
        .unit_price(price)
        .description(productDescription)
        .kcal_per_100g(energyHandler.handle(docProd))
        .build();
  }

}