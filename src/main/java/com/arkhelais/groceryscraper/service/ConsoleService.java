package com.arkhelais.groceryscraper.service;

import static com.arkhelais.groceryscraper.util.Constants.DEFAULT_OUTPUT_FILE;
import static com.arkhelais.groceryscraper.util.Constants.DEFAULT_URL;
import static com.arkhelais.groceryscraper.util.Constants.MESSAGE_ALREADY_EXIST;
import static com.arkhelais.groceryscraper.util.Constants.MESSAGE_DIRECTORY;
import static com.arkhelais.groceryscraper.util.Constants.MESSAGE_SAVED_TO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.SneakyThrows;
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
public class ConsoleService implements Runnable {

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

  @Override
  public void run() {
    String productsAsJson = (productsUrl == null) ?
        ProductScraper.extractProductsAsJson(DEFAULT_URL) :
        ProductScraper.extractProductsAsJson(productsUrl);

    if (isOutputToConsole()) {
      System.out.println(productsAsJson);
    }

    if (isOutputToFile()) {
      saveOutputToFile(productsAsJson);
    }
  }

  private boolean isOutputToConsole() {
    if (!outConsole && !outFile && fileName == null)
      return true;
    else
      return outConsole;
  }

  private boolean isOutputToFile() {
    boolean outputToFile = outFile;

    if (fileName == null) {
      fileName = DEFAULT_OUTPUT_FILE;
    } else {
      outputToFile = true;
    }
    return outputToFile;
  }

  private void saveOutputToFile(String output) {
    if (processFileRemoval()) {
      try {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
        bufferedWriter.write(output);
        System.out.println(MESSAGE_SAVED_TO + fileName);
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  @SneakyThrows(IOException.class)
  private boolean processFileRemoval() {
    Path path = Paths.get(fileName);
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