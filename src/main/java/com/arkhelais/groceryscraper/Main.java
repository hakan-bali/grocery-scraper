package com.arkhelais.groceryscraper;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

  public static void main(String[] args) {
    System.out.println("Hello Grocery Scraper");

    try {
      Document doc = Jsoup.connect("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html").get();

      Elements products = doc.getElementsByClass("productNameAndPromotions");

      for (Element product : products) {
        System.out.println("\n\nProduct Name = " + product.text());

        String productPageUrl = product.getElementsByTag("a").first().attr("abs:href");
        System.out.println("Product Page Url = " + productPageUrl);

        Document docProd = Jsoup.connect(productPageUrl).get();

        String priceRawText = docProd.getElementsByClass("pricePerUnit").text();
        String price = priceRawText.substring(1, priceRawText.indexOf("/"));
        System.out.println("Product Price = " + priceRawText + " >> " + price);

        String productDescription = docProd.getElementsByClass("productText").get(0).text();
        System.out.println("productDescription = " + productDescription);

        String energy;
        if (!docProd.getElementsByTag("td").isEmpty()) {
          energy = docProd.getElementsByTag("td").get(2).text();
          System.out.println("energy = " + energy);
        }
      }
    } catch (IOException e) {
      System.err.println("IOException occured");
    }
  }

}
