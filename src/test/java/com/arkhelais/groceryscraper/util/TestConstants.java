package com.arkhelais.groceryscraper.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestConstants {

  public static final Double GROSS = 39.5;
  public static final Double VAT = 6.58;
  public static final int OUTPUT_SIZE = 17;
  public static final int KCAL = 52;
  public static final String TITLE = "Sainsbury's Blackcurrants 150g";
  public static final String KCAL_TEST_INPUT1_1 = "<table><td>140kJ</td><td>-</td><td>33kcal</td></table>";
  public static final String KCAL_TEST_INPUT1_2 = "<table><td>140kJ</td><td>-</td><td>33kJ</td></table>";
  public static final String KCAL_TEST_INPUT1_3 = "<table><td>140kJ</td></table>";
  public static final Integer KCAL_TEST_OUTPUT1 = 33;
  public static final String KCAL_TEST_INPUT2_1 = "<table><td>140kJ</td><td>-</td><td>%2</td><td>52kcal</td></table>";
  public static final String KCAL_TEST_INPUT2_2 = "<table><td>140kJ</td><td>-</td><td>%2</td><td>52kJ</td></table>";
  public static final String KCAL_TEST_INPUT2_3 = "<table><td>140kJ</td><td>-</td><td>%2</td></table>";
  public static final Integer KCAL_TEST_OUTPUT2 = 52;
  public static final String KCAL_TEST_INPUT3_1 = "<table><td>140kJ</td><td>-</td><td>%2</td><td>99</td></table>";
  public static final String KCAL_TEST_INPUT3_2 = "<table><td>140kJ</td><td>-</td><td>%2</td><td>99kJ</td></table>";
  public static final String KCAL_TEST_INPUT3_3 = "<table><td>140kJ</td><td>-</td><td>%2</td></table>";
  public static final Integer KCAL_TEST_OUTPUT3 = 99;
  public static final String TAG_TD = "td";
  public static final String TEST_OUTPUT_FILENAME = "test.json";

}
