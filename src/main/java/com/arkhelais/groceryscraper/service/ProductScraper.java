package com.arkhelais.groceryscraper.service;

import static com.arkhelais.groceryscraper.util.Constants.CSS_PRODUCT;
import static com.arkhelais.groceryscraper.util.Constants.DEFAULT_URL;
import static com.arkhelais.groceryscraper.util.Constants.VAT;

import com.arkhelais.groceryscraper.dto.Output;
import com.arkhelais.groceryscraper.dto.Product;
import com.arkhelais.groceryscraper.dto.Total;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name="\"./gradlew run\" OR \"./gradlew run --args='options in brackets'\"",
    separator = " ",
    headerHeading = "%nUsage:",
    synopsisHeading = "%n",
    commandListHeading = "%nCommands:%n",
    descriptionHeading = "%nDescription:%n",
    parameterListHeading = "%nParameters:%n",
    optionListHeading = "%nOptions:%n",
    mixinStandardHelpOptions = true,
    version = "Grocery Scraper version:1.0-SNAPSHOT",
    description = "Grocery Scraper for Sainsbury’s Groceries website.")
public class ProductScraper implements Runnable {
  private final Output output;
  private final EnergyHandler energyHandler;

  @Option(names = "-c",
      description = "JSon output will be redirected to Console. Can be used with the '-f' option.")
  boolean outConsole;

  @Option(names = "-f",
      description = "JSon output will be redirected to File 'output.json'. Can be used with the '-c' option.")
  boolean outFile;

  @Option(names = "-x",
      description = "Output file is removed before output is saved.")
  boolean removeFile;

  @Option(names = "-n", paramLabel = "FILENAME",
      description = "Given <FILENAME> is used instead of default 'output.json'.")
  String fileName;

  public ProductScraper() {
    output = Output.builder().results(new ArrayList<>()).build();
    energyHandler =
        new NutritionTableOne(
            new NutritionTableTwo(
                new NutritionTableThree(null)));
  }

  public String extractProductsAsJson() {
    try {
      Elements products = Jsoup.connect(DEFAULT_URL).get().getElementsByClass(CSS_PRODUCT);
      if (!products.isEmpty()) {
        for (Element product : products) {
          output.getResults().add(getProductInfo(product));
        }
        output.setTotal(generateTotal());
      }
      return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(output);
    } catch (IOException e) {
      return e.getMessage();
    }
  }

  private Double getGross() {
    return output.getResults().stream()
        .map(Product::getUnitPrice)
        .reduce(0.0, Double::sum);
  }

  private Double getVat(Double gross) {
    return (gross * VAT) / (100 + VAT);
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

  private Product getProductInfo(Element product) {
    try {
      Document productDocument =
          Jsoup.connect(product.getElementsByTag("a").first().attr("abs:href")).get();
      Elements elements = productDocument.getElementsByTag("td");
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

  boolean isOutputToConsole() {
    if (!outConsole && !outFile && fileName == null)
      return true;
    else
      return outConsole;
  }

  boolean isOutputToFile() {
    boolean outputToFile = outFile;

    if (fileName == null) {
      fileName = "output.json";
    } else {
      outputToFile = true;
    }
    return outputToFile;
  }

  @SneakyThrows(IOException.class)
  private boolean processFileRemoval(Path path) {
    if (Files.isDirectory(path)) {
      System.out.println(fileName + " is a directory");
      return false;
    }
    if (fileName.equals("output.json")) {
      removeFile = true;
    }
    if (removeFile) {
      if (Files.exists(path)) {
        return Files.deleteIfExists(path);
      } else {
        return true;
      }
    } else {
      if (Files.exists(path)) {
        System.out.println(fileName + " already exists");
        return false;
      }
      return true;
    }
  }

  private void saveOutputToFile(String output) {
    Path path = Paths.get(fileName);
    if (processFileRemoval(path)) {
      try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
        bufferedWriter.write(output);
        System.out.println("JSon output saved to " + fileName);
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  @Override
  public void run() {
    String productsAsJson = extractProductsAsJson();

    if (isOutputToConsole()) {
      System.out.println(productsAsJson);
    }

    if (isOutputToFile()) {
      saveOutputToFile(productsAsJson);
    }
  }

}