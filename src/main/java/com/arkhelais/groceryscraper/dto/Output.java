package com.arkhelais.groceryscraper.dto;

import java.util.ArrayList;

// TODO : Revert Delombok operation when functionality ended.
public class Output {

  private ArrayList<Product> results;
  private Total total;

  public Output(ArrayList<Product> results, Total total) {
    this.results = results;
    this.total = total;
  }

  public Output() {
  }

  public static OutputBuilder builder() {
    return new OutputBuilder();
  }

  public ArrayList<Product> getResults() {
    return this.results;
  }

  public Total getTotal() {
    return this.total;
  }

  public void setResults(ArrayList<Product> results) {
    this.results = results;
  }

  public void setTotal(Total total) {
    this.total = total;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Output)) {
      return false;
    }
    final Output other = (Output) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    final Object this$results = this.getResults();
    final Object other$results = other.getResults();
    if (this$results == null ? other$results != null : !this$results.equals(other$results)) {
      return false;
    }
    final Object this$total = this.getTotal();
    final Object other$total = other.getTotal();
    if (this$total == null ? other$total != null : !this$total.equals(other$total)) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof Output;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $results = this.getResults();
    result = result * PRIME + ($results == null ? 43 : $results.hashCode());
    final Object $total = this.getTotal();
    result = result * PRIME + ($total == null ? 43 : $total.hashCode());
    return result;
  }

  public String toString() {
    return "Output(results=" + this.getResults() + ", total=" + this.getTotal() + ")";
  }

  public static class OutputBuilder {

    private ArrayList<Product> results;
    private Total total;

    OutputBuilder() {
    }

    public Output.OutputBuilder results(ArrayList<Product> results) {
      this.results = results;
      return this;
    }

    public Output.OutputBuilder total(Total total) {
      this.total = total;
      return this;
    }

    public Output build() {
      return new Output(results, total);
    }

    public String toString() {
      return "Output.OutputBuilder(results=" + this.results + ", total=" + this.total + ")";
    }
  }
}