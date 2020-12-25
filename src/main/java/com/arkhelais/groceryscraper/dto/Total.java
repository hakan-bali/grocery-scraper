package com.arkhelais.groceryscraper.dto;

import com.arkhelais.groceryscraper.util.CustomDoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Total {

  @JsonSerialize(using = CustomDoubleSerializer.class)
  private Double gross;
  @JsonSerialize(using = CustomDoubleSerializer.class)
  private Double vat;

}