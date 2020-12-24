package com.arkhelais.groceryscraper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Product {

  private String title;
  private Integer kcal_per_100g;
  private Double unit_price;
  private String description;

}