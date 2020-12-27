package com.arkhelais.groceryscraper.service;

import static com.arkhelais.groceryscraper.util.Constants.ATTR_HREF;
import static com.arkhelais.groceryscraper.util.Constants.CSS_PRICE;
import static com.arkhelais.groceryscraper.util.Constants.CSS_PRODUCT;
import static com.arkhelais.groceryscraper.util.Constants.CSS_PRODUCT_SUB_PAGE;
import static com.arkhelais.groceryscraper.util.Constants.DEFAULT_OUTPUT_FILE;
import static com.arkhelais.groceryscraper.util.Constants.DEFAULT_URL;
import static com.arkhelais.groceryscraper.util.Constants.MESSAGE_ALREADY_EXIST;
import static com.arkhelais.groceryscraper.util.Constants.MESSAGE_DIRECTORY;
import static com.arkhelais.groceryscraper.util.Constants.MESSAGE_NO_PRODUCT;
import static com.arkhelais.groceryscraper.util.Constants.MESSAGE_SAVED_TO;
import static com.arkhelais.groceryscraper.util.Constants.TAG_A;
import static com.arkhelais.groceryscraper.util.Constants.TAG_TD;
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

  @Option(names = "-u", paramLabel = "SOURCE_URL",
      description = "Given <SOURCE_URL> is used instead of default one given in the task description.")
  String productsUrl;

  public ProductScraper() {
    output = Output.builder().results(new ArrayList<>()).build();
    energyHandler =
        new NutritionTableOne(
            new NutritionTableTwo(
                new NutritionTableThree(null)));
  }

  @Override
  public void run() {
    String productsAsJson = (productsUrl == null) ?
        extractProductsAsJson(DEFAULT_URL) :
        extractProductsAsJson(productsUrl);

    if (isOutputToConsole()) {
      System.out.println(productsAsJson);
    }

    if (isOutputToFile()) {
      saveOutputToFile(productsAsJson);
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
      fileName = DEFAULT_OUTPUT_FILE;
    } else {
      outputToFile = true;
    }
    return outputToFile;
  }

  public String extractProductsAsJson(String url) {
    try {
      Elements products = Jsoup.connect(url).get().getElementsByClass(CSS_PRODUCT);
      if (!products.isEmpty()) {
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

  private Product getProductInfo(Element product) {
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

  private Double getUnitPrice(Document product) {
    try {
      String priceRawText = product.getElementsByClass(CSS_PRICE).text();
      return Double.parseDouble(priceRawText.substring(1, priceRawText.indexOf("/")));
    } catch (Exception e) {
      return 0.0;
    }
  }

  private String getDescription(Document product) {
    try {
      return product.getElementsByClass(CSS_PRODUCT_SUB_PAGE).get(0).text();
    } catch (Exception e) {
      return "";
    }
  }

  private Total generateTotal() {
    Double gross = getGross();
    return Total.builder()
        .gross(gross)
        .vat(getVat(gross))
        .build();
  }

  private Double getGross() {
    return output.getResults().stream()
        .map(Product::getUnitPrice)
        .reduce(0.0, Double::sum);
  }

  private Double getVat(Double gross) {
    return (gross * VAT) / (100 + VAT);
  }

  private void saveOutputToFile(String output) {
    Path path = Paths.get(fileName);
    if (processFileRemoval(path)) {
      try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
        bufferedWriter.write(output);
        System.out.println(MESSAGE_SAVED_TO + fileName);
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  @SneakyThrows(IOException.class)
  private boolean processFileRemoval(Path path) {
    if (Files.isDirectory(path)) {
      System.out.println(fileName + MESSAGE_DIRECTORY);
      return false;
    }
    if (fileName.equals(DEFAULT_OUTPUT_FILE)) {
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
        System.out.println(fileName + MESSAGE_ALREADY_EXIST);
        return false;
      }
      return true;
    }
  }

}