package com.arkhelais.groceryscraper.dto;

// TODO : Revert Delombok operation when functionality ended.
public class Total {

  private Double gross;
  private Double vat;

  public Total(Double gross, Double vat) {
    this.gross = gross;
    this.vat = vat;
  }

  public Total() {
  }

  public static TotalBuilder builder() {
    return new TotalBuilder();
  }

  public Double getGross() {
    return this.gross;
  }

  public Double getVat() {
    return this.vat;
  }

  public void setGross(Double gross) {
    this.gross = gross;
  }

  public void setVat(Double vat) {
    this.vat = vat;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Total)) {
      return false;
    }
    final Total other = (Total) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    final Object this$gross = this.getGross();
    final Object other$gross = other.getGross();
    if (this$gross == null ? other$gross != null : !this$gross.equals(other$gross)) {
      return false;
    }
    final Object this$vat = this.getVat();
    final Object other$vat = other.getVat();
    if (this$vat == null ? other$vat != null : !this$vat.equals(other$vat)) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof Total;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $gross = this.getGross();
    result = result * PRIME + ($gross == null ? 43 : $gross.hashCode());
    final Object $vat = this.getVat();
    result = result * PRIME + ($vat == null ? 43 : $vat.hashCode());
    return result;
  }

  public String toString() {
    return "Total(gross=" + this.getGross() + ", vat=" + this.getVat() + ")";
  }

  public static class TotalBuilder {

    private Double gross;
    private Double vat;

    TotalBuilder() {
    }

    public Total.TotalBuilder gross(Double gross) {
      this.gross = gross;
      return this;
    }

    public Total.TotalBuilder vat(Double vat) {
      this.vat = vat;
      return this;
    }

    public Total build() {
      return new Total(gross, vat);
    }

    public String toString() {
      return "Total.TotalBuilder(gross=" + this.gross + ", vat=" + this.vat + ")";
    }
  }
}