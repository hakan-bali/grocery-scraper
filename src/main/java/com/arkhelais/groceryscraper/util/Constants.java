package com.arkhelais.groceryscraper.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

  public static final String DEFAULT_URL =
      "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
  public static final Integer VAT = 20;   // Percentage
  public static final Integer KCAL_INDEX_2 = 2;
  public static final Integer KCAL_INDEX_3 = 3;
  public static final Integer KCAL_NULL = null;
  public static final String KCAL_LABEL = "kcal";
  public static final String TAG_TD = "td";
  public static final String TAG_A = "a";
  public static final String ATTR_HREF = "abs:href";
  public static final String CSS_PRICE = "pricePerUnit";
  public static final String CSS_PRODUCT = "productNameAndPromotions";
  public static final String CSS_PRODUCT_SUB_PAGE = "productText";
  public static final String DEFAULT_OUTPUT_FILE = "output.json";
  public static final String MESSAGE_NO_PRODUCT = "No product was found";
  public static final String MESSAGE_SAVED_TO = "JSon output saved to ";
  public static final String MESSAGE_DIRECTORY = " is a directory";
  public static final String MESSAGE_ALREADY_EXIST = " already exists";

}