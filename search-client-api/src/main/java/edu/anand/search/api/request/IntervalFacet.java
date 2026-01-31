package edu.anand.search.api.request;

public record IntervalFacet(String name, String field, String interval) implements Facet {
}