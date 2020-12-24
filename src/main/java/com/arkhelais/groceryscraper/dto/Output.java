package com.arkhelais.groceryscraper.dto;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Output {

  private ArrayList<Product> results;
  private Total total;

}