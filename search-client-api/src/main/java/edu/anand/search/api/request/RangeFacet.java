package edu.anand.search.api.request;

import java.util.List;

public record RangeFacet<T>(String name, String field, List<Range<T>> ranges) implements Facet {
}
