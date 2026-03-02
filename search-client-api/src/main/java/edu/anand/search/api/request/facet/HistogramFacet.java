package edu.anand.search.api.request.facet;

public record HistogramFacet(String name, String field, Double interval) implements Facet {

  @Override
  public String toString() {
    return "histogram{" + "field='" + field + ", interval=" + interval + '}';
  }
}
