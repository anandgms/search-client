package edu.anand.search.api.result;

import java.util.Map;

public record RangeFacetResult(String name, Map<String, Long> ranges) implements FacetResult {
}
