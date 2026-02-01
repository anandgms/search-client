package edu.anand.search.api.request.facet;

public record TermFacet(String name, String field, int limit) implements Facet {
}
