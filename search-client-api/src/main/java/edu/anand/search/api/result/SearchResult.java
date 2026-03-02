package edu.anand.search.api.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.anand.search.api.dto.NamedField;
import edu.anand.search.api.result.facet.FacetResult;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResult implements Serializable {

  @JsonProperty private List<NamedField> documents;

  @JsonProperty private long totalHits;

  @JsonProperty private Map<String, FacetResult> facets;

  @JsonProperty private Map<String, StatsResult> stats;

  @JsonProperty private Map<String, HighlightResult> highlights;

  public final List<NamedField> documents() {
    return documents;
  }

  public final SearchResult setDocuments(List<NamedField> documents) {
    this.documents = documents;
    return this;
  }

  public final long totalHits() {
    return totalHits;
  }

  public final SearchResult setTotalHits(long totalHits) {
    this.totalHits = totalHits;
    return this;
  }

  public final Map<String, FacetResult> facets() {
    return facets;
  }

  public final SearchResult setFacets(Map<String, FacetResult> facets) {
    this.facets = facets;
    return this;
  }

  public final SearchResult addFacet(String key, FacetResult facet) {
    if (facets == null) {
      facets = new HashMap<>();
    }
    facets.put(key, facet);
    return this;
  }

  public final Map<String, StatsResult> statistics() {
    return stats;
  }

  public final SearchResult setStatistics(Map<String, StatsResult> stats) {
    this.stats = stats;
    return this;
  }

  public final Map<String, HighlightResult> highlights() {
    return highlights;
  }

  public final SearchResult setHighlights(Map<String, HighlightResult> highlights) {
    this.highlights = highlights;
    return this;
  }
}
