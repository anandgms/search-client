package edu.anand.search.api.result.facet;

import java.util.Map;

public record RangeFacetResult(String name, Map<String, Long> ranges) implements FacetResult {
  public void addResult(String key, Long count) {
    ranges.put(key, count);
  }
}
