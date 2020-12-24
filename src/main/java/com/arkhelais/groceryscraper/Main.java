package com.arkhelais.groceryscraper;

import static com.arkhelais.groceryscraper.util.Constants.CSS_PRODUCT;
import static com.arkhelais.groceryscraper.util.Constants.DEFAULT_URL;
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

public class Main {

  public static void main(String[] args) {
    Double price;
    Double gross = 0.0;
    Integer kcal = 0;
    Integer kcalIndex;

    try {
      Document doc = Jsoup.connect(DEFAULT_URL).get();
      Elements products = doc.getElementsByClass(CSS_PRODUCT);
      Output output = Output.builder()
          .results(new ArrayList<>())
          .build();

      String productPageUrl;
      Document docProd;
      String priceRawText;
      String priceText;
      String productDescription;
      String energy = "";

      for (Element product : products) {
        productPageUrl = product.getElementsByTag("a").first().attr("abs:href");
        docProd = Jsoup.connect(productPageUrl).get();
        priceRawText = docProd.getElementsByClass("pricePerUnit").text();
        priceText = priceRawText.substring(1, priceRawText.indexOf("/"));
        productDescription = docProd.getElementsByClass("productText").get(0).text();
        energy = "";
        if (!docProd.getElementsByTag("td").isEmpty()) {
          energy = docProd.getElementsByTag("td").get(2).text();
        }

        price = Double.parseDouble(priceText);
        gross += price;
        kcal = null;
        kcalIndex = energy.indexOf("kcal");
        if (kcalIndex > 0) {
          kcal = Integer.parseInt(energy.substring(0, kcalIndex));
        }
        output.getResults().add(
            Product.builder()
                .title(product.text())
                .unit_price(price)
                .description(productDescription)
                .kcal_per_100g(kcal)
                .build());
      }
      Double vat = (gross * 100) / (100 + VAT);
      vat = Double.parseDouble(String.format("%.2f", vat));
      output.setTotal(Total.builder().gross(gross).vat(vat).build());

      ObjectMapper mapper = new ObjectMapper();
      String jsonOutput = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(output);
      System.out.println("Beautified JSon Output\n----------------------------\n" + jsonOutput);

    } catch (IOException e) {
      System.err.println("\nIOException occured\n");
    }
  }

}