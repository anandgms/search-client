package edu.anand.search.api.result;

import java.util.Map;

public record IntervalFacetResult(String name, Map<String, Long> intervals) implements FacetResult {
}
