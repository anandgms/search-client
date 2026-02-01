package edu.anand.search.api.request.facet;

public record HistogramFacet(String name, String field, int limit, Double interval) implements Facet {
}