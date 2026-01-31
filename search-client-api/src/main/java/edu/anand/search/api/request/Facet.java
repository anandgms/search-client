package edu.anand.search.api.request;

public sealed interface Facet permits TermFacet, RangeFacet, IntervalFacet {
    String name();
}
