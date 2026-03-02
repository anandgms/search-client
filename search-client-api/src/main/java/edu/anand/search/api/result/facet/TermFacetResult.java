package edu.anand.search.api.result.facet;

import java.util.Map;

public record TermFacetResult(String name, Map<String, Long> buckets) implements FacetResult {

  public void addResult(String key, Long count) {
    buckets.put(key, count);
  }
}
