package edu.anand.search.api.result;

public sealed interface FacetResult permits TermFacetResult, RangeFacetResult, IntervalFacetResult {
    String name();
}
