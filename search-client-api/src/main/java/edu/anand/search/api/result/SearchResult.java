package edu.anand.search.api.result;

import edu.anand.search.api.dto.NamedField;

import java.util.List;
import java.util.Map;

public class SearchResult {
    private List<NamedField> documents;
    private long totalHits;
    private Map<String, FacetResult> facets;
    private Map<String, StatsResult> stats;
    private Map<String, HighlightResult> highlights;

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