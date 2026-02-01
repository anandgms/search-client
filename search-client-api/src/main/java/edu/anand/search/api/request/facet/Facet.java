package edu.anand.search.api.request.facet;

public sealed interface Facet permits TermFacet,
        NumericRangeFacet,
        DateRangeFacet,
        HistogramFacet,
        DateHistogramFacet {
    String name();
}
