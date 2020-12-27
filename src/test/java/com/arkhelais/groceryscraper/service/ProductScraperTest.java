package com.arkhelais.groceryscraper.service;

import static com.arkhelais.groceryscraper.util.Constants.CSS_PRODUCT;
import static com.arkhelais.groceryscraper.util.Constants.DEFAULT_URL;
import static com.arkhelais.groceryscraper.util.TestConstants.OUTPUT_SIZE_EMPTY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.arkhelais.groceryscraper.Main;
import com.arkhelais.groceryscraper.dto.Output;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class ProductScraperTest {

  ProductScraper productScraper;

  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
  private final PrintStream standardOut = System.out;

  @BeforeEach
  void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));
    productScraper = new ProductScraper();
  }

  @AfterEach
  void tearDown() {
    System.setOut(standardOut);
  }

  @Test
  void givenEmptyHtmlElementsWhenApplicationRunThenResultIsEmpty() throws IOException {
    // Arrange
    Connection connection = Mockito.mock(Connection.class);
    Document document = Mockito.mock(Document.class);
    Elements elements = new Elements();
    MockedStatic<Jsoup> mb = Mockito.mockStatic(Jsoup.class);

    mb.when(() -> Jsoup.connect(DEFAULT_URL)).thenReturn(connection);
    when(connection.get()).thenReturn(document);
    when(document.getElementsByClass((CSS_PRODUCT))).thenReturn(elements);

    // Act
    Main.main(null);

    // Assert
    Output output = new ObjectMapper().readValue(outputStreamCaptor.toString().trim(), Output.class);
    assertEquals(OUTPUT_SIZE_EMPTY, output.getResults().size());
  }

}