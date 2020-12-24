package com.arkhelais.groceryscraper.dto;

// TODO : Revert Delombok operation when functionality ended.
public class Product {

  private String title;
  private Integer kcal_per_100g;
  private Double unit_price;
  private String description;

  public Product(String title, Integer kcal_per_100g, Double unit_price, String description) {
    this.title = title;
    this.kcal_per_100g = kcal_per_100g;
    this.unit_price = unit_price;
    this.description = description;
  }

  public Product() {
  }

  public static ProductBuilder builder() {
    return new ProductBuilder();
  }

  public String getTitle() {
    return this.title;
  }

  public Integer getKcal_per_100g() {
    return this.kcal_per_100g;
  }

  public Double getUnit_price() {
    return this.unit_price;
  }

  public String getDescription() {
    return this.description;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setKcal_per_100g(Integer kcal_per_100g) {
    this.kcal_per_100g = kcal_per_100g;
  }

  public void setUnit_price(Double unit_price) {
    this.unit_price = unit_price;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Product)) {
      return false;
    }
    final Product other = (Product) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    final Object this$title = this.getTitle();
    final Object other$title = other.getTitle();
    if (this$title == null ? other$title != null : !this$title.equals(other$title)) {
      return false;
    }
    final Object this$kcal_per_100g = this.getKcal_per_100g();
    final Object other$kcal_per_100g = other.getKcal_per_100g();
    if (this$kcal_per_100g == null ? other$kcal_per_100g != null
        : !this$kcal_per_100g.equals(other$kcal_per_100g)) {
      return false;
    }
    final Object this$unit_price = this.getUnit_price();
    final Object other$unit_price = other.getUnit_price();
    if (this$unit_price == null ? other$unit_price != null
        : !this$unit_price.equals(other$unit_price)) {
      return false;
    }
    final Object this$description = this.getDescription();
    final Object other$description = other.getDescription();
    if (this$description == null ? other$description != null
        : !this$description.equals(other$description)) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof Product;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $title = this.getTitle();
    result = result * PRIME + ($title == null ? 43 : $title.hashCode());
    final Object $kcal_per_100g = this.getKcal_per_100g();
    result = result * PRIME + ($kcal_per_100g == null ? 43 : $kcal_per_100g.hashCode());
    final Object $unit_price = this.getUnit_price();
    result = result * PRIME + ($unit_price == null ? 43 : $unit_price.hashCode());
    final Object $description = this.getDescription();
    result = result * PRIME + ($description == null ? 43 : $description.hashCode());
    return result;
  }

  public String toString() {
    return "Product(title=" + this.getTitle() + ", kcal_per_100g=" + this.getKcal_per_100g()
        + ", unit_price=" + this.getUnit_price() + ", description=" + this.getDescription() + ")";
  }

  public static class ProductBuilder {

    private String title;
    private Integer kcal_per_100g;
    private Double unit_price;
    private String description;

    ProductBuilder() {
    }

    public Product.ProductBuilder title(String title) {
      this.title = title;
      return this;
    }

    public Product.ProductBuilder kcal_per_100g(Integer kcal_per_100g) {
      this.kcal_per_100g = kcal_per_100g;
      return this;
    }

    public Product.ProductBuilder unit_price(Double unit_price) {
      this.unit_price = unit_price;
      return this;
    }

    public Product.ProductBuilder description(String description) {
      this.description = description;
      return this;
    }

    public Product build() {
      return new Product(title, kcal_per_100g, unit_price, description);
    }

    public String toString() {
      return "Product.ProductBuilder(title=" + this.title + ", kcal_per_100g=" + this.kcal_per_100g
          + ", unit_price=" + this.unit_price + ", description=" + this.description + ")";
    }
  }
}