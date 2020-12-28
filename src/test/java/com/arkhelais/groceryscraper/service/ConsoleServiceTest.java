package com.arkhelais.groceryscraper.service;

import static com.arkhelais.groceryscraper.util.Constants.CSS_PRODUCT;
import static com.arkhelais.groceryscraper.util.Constants.DEFAULT_URL;
import static com.arkhelais.groceryscraper.util.Constants.MESSAGE_NO_PRODUCT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.arkhelais.groceryscraper.Main;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.MockedStatic.Verification;
import org.mockito.Mockito;

class ConsoleServiceTest extends Main {

  static MockedStatic<Jsoup> staticJsoup;

  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
  private final PrintStream standardOut = System.out;

  @BeforeAll
  static void setupClass() {
    staticJsoup = Mockito.mockStatic(Jsoup.class);
  }

  @AfterAll
  static void tearDownClass() {
    staticJsoup.close();
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
  void run() throws IOException {
    // Arrange
    Connection connection = Mockito.mock(Connection.class);
    Document document = Mockito.mock(Document.class);
    staticJsoup.when(new Verification() {
      @Override
      public void apply() throws Throwable {
        Jsoup.connect(DEFAULT_URL);
      }
    }).thenReturn(connection);
    when(connection.get()).thenReturn(document);
    when(document.getElementsByClass((CSS_PRODUCT))).thenReturn(new Elements());

    // Act
    ConsoleService consoleService = new ConsoleService();
    consoleService.run();

    // Assert
    assertTrue(outputStreamCaptor.toString().contains(MESSAGE_NO_PRODUCT));
  }

}