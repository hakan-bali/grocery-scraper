package com.arkhelais.groceryscraper.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.DecimalFormat;

public class CustomDoubleSerializer extends JsonSerializer<Double> {

  @Override
  public void serialize(Double value, JsonGenerator jgen, SerializerProvider provider)
      throws IOException {
    if (null == value) {
      jgen.writeNull();
    } else {
      final String pattern = "###,###,##0.00";
      final DecimalFormat myFormatter = new DecimalFormat(pattern);
      final String output = myFormatter.format(value);
      jgen.writeNumber(output);
    }
  }

}
