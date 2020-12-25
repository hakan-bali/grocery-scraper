package com.arkhelais.groceryscraper.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

  private String title;
  @JsonInclude(Include.NON_NULL)
  @JsonProperty("kcal_per_100g")
  private Integer kcal;
  @JsonProperty("unit_price")
  private Double unitPrice;
  private String description;

}