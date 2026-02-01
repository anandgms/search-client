package edu.anand.search.api.request.facet;

import java.util.List;

public record NumericRangeFacet(String name, String field, int limit, List<Range<Float>> ranges) implements Facet {
}
