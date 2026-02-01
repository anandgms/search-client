package edu.anand.search.api.request.facet;

import java.time.temporal.ChronoField;

public record DateHistogramFacet(String name, String field, int limit, ChronoField chronos) implements Facet {
}