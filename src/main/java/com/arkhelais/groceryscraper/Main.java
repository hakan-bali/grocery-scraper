package com.arkhelais.groceryscraper;

import static com.arkhelais.groceryscraper.util.Constants.CSS_PRODUCT;
import static com.arkhelais.groceryscraper.util.Constants.DEFAULT_URL;
import static com.arkhelais.groceryscraper.util.Constants.VAT;

import com.arkhelais.groceryscraper.dto.Output;
import com.arkhelais.groceryscraper.dto.Product;
import com.arkhelais.groceryscraper.dto.Total;
import com.arkhelais.groceryscraper.service.EnergyHandler;
import com.arkhelais.groceryscraper.service.NutritionTableOne;
import com.arkhelais.groceryscraper.service.NutritionTableThree;
import com.arkhelais.groceryscraper.service.NutritionTableTwo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

  public static void main(String[] args) {
    EnergyHandler energyHandlerOne = new NutritionTableOne();
    EnergyHandler energyHandlerTwo = new NutritionTableTwo();
    EnergyHandler energyHandlerThree = new NutritionTableThree();
    energyHandlerOne.setNext(energyHandlerTwo);
    energyHandlerTwo.setNext(energyHandlerThree);

    //String categoryPageUrl = "https://git-scm.com/book/en/v2/Git-Basics-Getting-a-Git-Repository";
    String categoryPageUrl = DEFAULT_URL;

    Double price;
    Double gross = 0.0;

    try {
      Output output = Output.builder()
          .results(new ArrayList<>())
          .build();
      Elements products = Jsoup.connect(categoryPageUrl).get().getElementsByClass(CSS_PRODUCT);

      String productPageUrl;
      Document docProd;
      String priceRawText;
      String priceText;
      String productDescription;

      for (Element product : products) {
        productPageUrl = product.getElementsByTag("a").first().attr("abs:href");
        docProd = Jsoup.connect(productPageUrl).get();
        priceRawText = docProd.getElementsByClass("pricePerUnit").text();
        priceText = priceRawText.substring(1, priceRawText.indexOf("/"));
        productDescription = docProd.getElementsByClass("productText").get(0).text();

        price = Double.parseDouble(priceText);
        gross += price;

        output.getResults().add(
            Product.builder()
                .title(product.text())
                .url(productPageUrl)
                .unit_price(price)
                .description(productDescription)
                .kcal_per_100g(energyHandlerOne.handle(docProd))
                .build());
      }
      Double vat = (gross * 100) / (100 + VAT);
      vat = Double.parseDouble(String.format("%.2f", vat));
      output.setTotal(Total.builder().gross(gross).vat(vat).build());

      ObjectMapper mapper = new ObjectMapper();
      String jsonOutput = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(output);
      System.out.println("Beautified JSon Output\n----------------------------\n" + jsonOutput);

      /*
      Double tot = output.getResults().stream()
          .map(Product::getUnit_price)
          .reduce(0.0, Double::sum);
      System.out.println("tot = " + tot);

       */

    } catch (IOException e) {
      System.err.println("\nIOException occured\n");
    }
  }

}