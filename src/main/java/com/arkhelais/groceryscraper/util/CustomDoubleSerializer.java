package com.arkhelais.groceryscraper.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.DecimalFormat;

public class CustomDoubleSerializer extends JsonSerializer<Double> {

  @Override
  public void serialize(Double value, JsonGenerator jsonGenerator, SerializerProvider provider)
      throws IOException {
    if (null == value) {
      jsonGenerator.writeNull();
    } else {
      final String pattern = "###,###,##0.00";
      final DecimalFormat myFormatter = new DecimalFormat(pattern);
      final String output = myFormatter.format(value);
      jsonGenerator.writeNumber(output);
    }
  }

}
