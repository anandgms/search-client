package edu.anand.search.api.result;

import java.util.Map;

public record TermFacetResult(String name, Map<String, Long> buckets) implements FacetResult {
}
