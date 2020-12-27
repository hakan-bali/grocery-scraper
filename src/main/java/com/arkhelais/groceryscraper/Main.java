package com.arkhelais.groceryscraper;

import com.arkhelais.groceryscraper.service.ConsoleService;
import picocli.CommandLine;

public class Main {

  public static void main(String[] args) {
    CommandLine commandLine = new CommandLine(new ConsoleService());
    commandLine.execute(args);
  }

}