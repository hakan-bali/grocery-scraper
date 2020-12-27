package com.arkhelais.groceryscraper;

import static com.arkhelais.groceryscraper.util.TestConstants.GROSS;
import static com.arkhelais.groceryscraper.util.TestConstants.KCAL;
import static com.arkhelais.groceryscraper.util.TestConstants.OUTPUT_SIZE;
import static com.arkhelais.groceryscraper.util.TestConstants.TITLE;
import static com.arkhelais.groceryscraper.util.TestConstants.VAT;
import static org.junit.jupiter.api.Assertions.*;

import com.arkhelais.groceryscraper.dto.Output;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {

  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
  private final PrintStream standardOut = System.out;

  @BeforeEach
  void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @AfterEach
  void tearDown() {
    System.setOut(standardOut);
  }

  @Test
  void givenNothingWhenApplicationRunThenJsonOutputToConsoleIsOk() throws JsonProcessingException {
    Main.main(new String[]{"-c"});

    Output output = new ObjectMapper().readValue(outputStreamCaptor.toString().trim(), Output.class);
    if (output == null)
      return;
    assertEquals(GROSS, output.getTotal().getGross());
    assertEquals(VAT, output.getTotal().getVat());
    assertEquals(OUTPUT_SIZE, output.getResults().size());
    assertEquals(KCAL, output.getResults().get(10).getKcal());
    assertEquals(TITLE, output.getResults().get(15).getTitle());
    assertNull(output.getResults().get(16).getKcal());
  }

}