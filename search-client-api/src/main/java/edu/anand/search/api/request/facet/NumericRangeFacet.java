package edu.anand.search.api.request.facet;

import java.util.List;

public record NumericRangeFacet(String name, String field, List<Range<Float>> ranges)
    implements Facet {

  @Override
  public String toString() {
    return "numericRange{" + "field='" + field + ", ranges=" + ranges + '}';
  }
}
