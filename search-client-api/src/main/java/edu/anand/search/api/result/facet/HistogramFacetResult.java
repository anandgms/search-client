package edu.anand.search.api.result.facet;

import java.util.Map;

public record HistogramFacetResult(String name, Map<String, Long> intervals)
    implements FacetResult {

  public void addResult(Object key, long value) {
    intervals.put(String.valueOf(key), value);
  }
}
