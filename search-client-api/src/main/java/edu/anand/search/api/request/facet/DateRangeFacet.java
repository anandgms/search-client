package edu.anand.search.api.request.facet;

import java.util.List;

public record DateRangeFacet(String name, String field, List<Range<String>> ranges) implements Facet {

    @Override
    public String toString() {
        return "dateRange{" +
                "field='" + field +
                ", ranges=" + ranges +
                '}';
    }
}
