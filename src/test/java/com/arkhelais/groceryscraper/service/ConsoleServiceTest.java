package com.arkhelais.groceryscraper.service;

import static com.arkhelais.groceryscraper.util.Constants.DEFAULT_OUTPUT_FILE;
import static com.arkhelais.groceryscraper.util.Constants.DEFAULT_URL;
import static com.arkhelais.groceryscraper.util.Constants.MESSAGE_NO_PRODUCT;
import static com.arkhelais.groceryscraper.util.Constants.MESSAGE_SAVED_TO;
import static com.arkhelais.groceryscraper.util.TestConstants.TEST_OUTPUT_FILENAME;
import static org.junit.jupiter.api.Assertions.*;

import com.arkhelais.groceryscraper.Main;
import com.arkhelais.groceryscraper.dto.Output;
import com.arkhelais.groceryscraper.dto.Product;
import com.arkhelais.groceryscraper.dto.Total;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.MockedStatic.Verification;
import org.mockito.Mockito;
import picocli.CommandLine;

class ConsoleServiceTest extends Main {

  static MockedStatic<ProductScraper> staticProductScraper;

  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
  private final PrintStream standardOut = System.out;

  @BeforeAll
  static void setupClass() {
    staticProductScraper = Mockito.mockStatic(ProductScraper.class);
  }

  @AfterAll
  static void tearDownClass() {
    staticProductScraper.close();
  }

  @BeforeEach
  void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @AfterEach
  void tearDown() {
    System.setOut(standardOut);
  }

  @Test
  void givenOutputToConsoleWhenWriteToConsoleThenContentIsCorrect() {
    // Arrange
    stubProductScraper(MESSAGE_NO_PRODUCT);

    // Act
    CommandLine commandLine = new CommandLine(new ConsoleService());
    commandLine.execute("-c");

    // Assert
    assertTrue(outputStreamCaptor.toString().contains(MESSAGE_NO_PRODUCT));
  }

  @Test
  void givenOutputToFileWhenWriteToFileThenContentIsCorrect()
      throws IOException {
    // Arrangement
    Output expectedOutputDto = buildOutputDto();
    stubProductScraper(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedOutputDto));

    // Act
    CommandLine commandLine = new CommandLine(new ConsoleService());
    commandLine.execute("-f");

    // Assert
    assertTrue(outputStreamCaptor.toString().contains(MESSAGE_SAVED_TO));
    Output actualOutput = new ObjectMapper().readValue(Paths.get(DEFAULT_OUTPUT_FILE).toFile(), Output.class);
    assertEquals(expectedOutputDto.getResults().size(), actualOutput.getResults().size());
    assertEquals(expectedOutputDto.getTotal().getGross(), actualOutput.getTotal().getGross());
    assertEquals(expectedOutputDto.getTotal().getVat(), actualOutput.getTotal().getVat());
  }

  @Test
  void givenOutputToConsoleAndFileWhenWriteToConsoleAndFileThenContentIsCorrect()
      throws IOException {
    // Arrangement
    Output expectedOutputDto = buildOutputDto();
    stubProductScraper(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedOutputDto));

    // Act
    CommandLine commandLine = new CommandLine(new ConsoleService());
    commandLine.execute("-cf");

    // Assert
    Output actualOutputDto = new ObjectMapper().readValue(Paths.get(DEFAULT_OUTPUT_FILE).toFile(), Output.class);
    assertTrue(outputStreamCaptor.toString().contains(MESSAGE_SAVED_TO));
    assertTrue(outputStreamCaptor.toString().contains(expectedOutputDto.getResults().get(0).getTitle()));
    assertEquals(expectedOutputDto.getResults().size(), actualOutputDto.getResults().size());
    assertEquals(expectedOutputDto.getTotal().getGross(), actualOutputDto.getTotal().getGross());
    assertEquals(expectedOutputDto.getTotal().getVat(), actualOutputDto.getTotal().getVat());
  }

  @Test
  void givenOutputToOptionFileNameWhenWriteToFileThenContentIsCorrect()
      throws IOException {
    // Arrangement
    Output expectedOutputDto = buildOutputDto();
    stubProductScraper(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedOutputDto));

    // Act
    CommandLine commandLine = new CommandLine(new ConsoleService());
    commandLine.execute("-fxn " + TEST_OUTPUT_FILENAME);

    // Assert
    assertTrue(outputStreamCaptor.toString().contains(MESSAGE_SAVED_TO));
    Output actualOutputDto = new ObjectMapper().readValue(Paths.get(TEST_OUTPUT_FILENAME).toFile(), Output.class);
    assertEquals(expectedOutputDto.getResults().size(), actualOutputDto.getResults().size());
    assertEquals(expectedOutputDto.getTotal().getGross(), actualOutputDto.getTotal().getGross());
    assertEquals(expectedOutputDto.getTotal().getVat(), actualOutputDto.getTotal().getVat());
  }

  private void stubProductScraper(String mockData) {
    staticProductScraper.when(new Verification() {
      @Override
      public void apply() {
        ProductScraper.extractProductsAsJson(DEFAULT_URL);
      }
    }).thenReturn(mockData);
  }

  public static Output buildOutputDto() {
    Product product = Product.builder()
        .unitPrice(1.99)
        .kcal(99)
        .description("First Quality Berry")
        .title("Lila Berry")
        .build();
    List<Product> results = new ArrayList<>(Collections.singletonList(product));
    Total total = Total.builder().gross(5.0).vat(0.83).build();
    return Output.builder()
        .results(results)
        .total(total)
        .build();
  }

}