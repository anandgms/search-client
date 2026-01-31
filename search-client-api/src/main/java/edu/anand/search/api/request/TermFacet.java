package edu.anand.search.api.request;

public record TermFacet(String name, String field, int limit) implements Facet {
}
