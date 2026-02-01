package edu.anand.search.api.result.facet;

public sealed interface FacetResult permits TermFacetResult, RangeFacetResult, HistogramFacetResult {
    String name();
}
