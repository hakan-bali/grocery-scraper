package com.arkhelais.groceryscraper.service;

import static com.arkhelais.groceryscraper.util.TestConstants.KCAL_TEST_INPUT1_1;
import static com.arkhelais.groceryscraper.util.TestConstants.KCAL_TEST_INPUT1_2;
import static com.arkhelais.groceryscraper.util.TestConstants.KCAL_TEST_INPUT1_3;
import static com.arkhelais.groceryscraper.util.TestConstants.KCAL_TEST_OUTPUT1;
import static org.junit.jupiter.api.Assertions.*;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NutritionTableOneTest {

  private final NutritionTableOne nutritionTableOne =
      new NutritionTableOne(
          new NutritionTableTwo(
              new NutritionTableThree(null)));

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void givenKcalValueWhenHandleThenResultIsOk() {
    final Elements elements = Jsoup.parse(KCAL_TEST_INPUT1_1).getElementsByTag("td");
    Integer result = nutritionTableOne.handle(elements);
    assertEquals(KCAL_TEST_OUTPUT1, result);
  }

  @Test
  void givenWrongKcalValueWhenHandleThenResultIsNull() {
    final Elements elements = Jsoup.parse(KCAL_TEST_INPUT1_2).getElementsByTag("td");
    Integer result = nutritionTableOne.handle(elements);
    assertNull(result);
  }

  @Test
  void givenMissingTagWhenHandleThenResultIsNull() {
    final Elements elements = Jsoup.parse(KCAL_TEST_INPUT1_3).getElementsByTag("td");
    Integer result = nutritionTableOne.handle(elements);
    assertNull(result);
  }

  @Test
  void givenKcalValueWhenEnergyHandlersOrderChangedThenResultIsOk() {
    final NutritionTableThree nutritionTableThree =
        new NutritionTableThree(
            new NutritionTableTwo(
                new NutritionTableOne(null)));

    final Elements elements = Jsoup.parse(KCAL_TEST_INPUT1_1).getElementsByTag("td");
    Integer result = nutritionTableThree.handle(elements);
    assertEquals(KCAL_TEST_OUTPUT1, result);
  }

}