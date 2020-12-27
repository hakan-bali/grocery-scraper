package com.arkhelais.groceryscraper;

import com.arkhelais.groceryscraper.service.ProductScraper;
import picocli.CommandLine;

public class Main {

  public static void main(String[] args) {
    CommandLine commandLine = new CommandLine(new ProductScraper());
    commandLine.execute(args);
  }

}