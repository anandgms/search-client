package edu.anand.search.api.request.facet;

import java.time.temporal.ChronoField;

public record DateHistogramFacet(String name, String field, ChronoField chronos) implements Facet {

  @Override
  public String toString() {
    return "dateHistogram{" + "field='" + field + ", chronos=" + chronos + '}';
  }
}
