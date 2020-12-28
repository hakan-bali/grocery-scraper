package com.arkhelais.groceryscraper.service;

import static com.arkhelais.groceryscraper.util.Constants.ATTR_HREF;
import static com.arkhelais.groceryscraper.util.Constants.CSS_PRICE;
import static com.arkhelais.groceryscraper.util.Constants.CSS_PRODUCT;
import static com.arkhelais.groceryscraper.util.Constants.CSS_PRODUCT_SUB_PAGE;
import static com.arkhelais.groceryscraper.util.Constants.MESSAGE_NO_PRODUCT;
import static com.arkhelais.groceryscraper.util.Constants.TAG_A;
import static com.arkhelais.groceryscraper.util.Constants.TAG_TD;
import static com.arkhelais.groceryscraper.util.Constants.VAT;

import com.arkhelais.groceryscraper.dto.Output;
import com.arkhelais.groceryscraper.dto.Product;
import com.arkhelais.groceryscraper.dto.Total;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ProductScraper {
  private static Output output = Output.builder().results(new ArrayList<Product>()).build();
  private static final EnergyHandler energyHandler =
      new NutritionTableOne(
          new NutritionTableTwo(
              new NutritionTableThree(null)));

  private ProductScraper() {
  }

  public static String extractProductsAsJson(String url) {
    output = Output.builder().results(new ArrayList<Product>()).build();
    try {
      Elements products = Jsoup.connect(url).get().getElementsByClass(CSS_PRODUCT);
      if (!products.isEmpty()) {
        output.getResults().clear();
        for (Element product : products) {
          output.getResults().add(getProductInfo(product));
        }
        output.setTotal(generateTotal());
      }
      return output.getResults().isEmpty()
          ? MESSAGE_NO_PRODUCT
          : new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(output);
    } catch (Exception e) {
      return e.getMessage();
    }
  }

  private static Product getProductInfo(Element product) {
    try {
      Document productDocument =
          Jsoup.connect(product.getElementsByTag(TAG_A).first().attr(ATTR_HREF)).get();
      Elements elements = productDocument.getElementsByTag(TAG_TD);
      return Product.builder()
          .title(product.text())
          .unitPrice(getUnitPrice(productDocument))
          .description(getDescription(productDocument))
          .kcal(energyHandler.handle(elements))
          .build();
    } catch (IOException e) {
      return null;
    }
  }

  private static Double getUnitPrice(Document product) {
    try {
      String priceRawText = product.getElementsByClass(CSS_PRICE).text();
      return Double.parseDouble(priceRawText.substring(1, priceRawText.indexOf("/")));
    } catch (Exception e) {
      return 0.0;
    }
  }

  private static String getDescription(Document product) {
    try {
      return product.getElementsByClass(CSS_PRODUCT_SUB_PAGE).get(0).text();
    } catch (Exception e) {
      return "";
    }
  }

  private static Total generateTotal() {
    Double gross = getGross();
    return Total.builder()
        .gross(gross)
        .vat(getVat(gross))
        .build();
  }

  private static Double getGross() {
    double acc = 0.0;
    for (Product product : output.getResults()) {
      Double unitPrice = product.getUnitPrice();
      acc = acc + unitPrice;
    }
    return acc;
  }

  private static Double getVat(Double gross) {
    return (gross * VAT) / (100 + VAT);
  }

}