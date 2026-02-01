package edu.anand.search.api.request.facet;

import java.util.List;

public record DateRangeFacet(String name, String field, int limit, List<Range<String>> ranges) implements Facet {
}
